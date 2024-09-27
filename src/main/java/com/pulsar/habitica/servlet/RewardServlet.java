package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.reward.RewardDaoImpl;
import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.RewardService;
import com.pulsar.habitica.util.JspHelper;
import com.pulsar.habitica.util.ServletUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rewards")
public class RewardServlet extends HttpServlet {

    private RewardService rewardService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        var rewardDao = RewardDaoImpl.getInstance();
        rewardService = new RewardService(rewardDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var user = ServletUtil.getAuthenticatedUser(request);
            var rewardId = request.getParameter("id");

            if (rewardId == null) {
                processAllRewards(request, response, user.getId());
            }else {
                processSingleReward(request, response, rewardId);
            }
        }catch (Exception e) {
            ServletUtil.handleException(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        var rewardHeading = request.getParameter("rewardHeading");
        var rewardDto = RewardDto.builder()
                .heading(rewardHeading)
                .userId(user.getId())
                .build();
        var reward = rewardService.createReward(rewardDto);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var id = request.getParameter("id");
        int rewardId;

        try {
            rewardId = Integer.parseInt(id);
        }catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        rewardService.deleteReward(rewardId);
    }

    private void processAllRewards(HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        List<Reward> rewards = rewardService.findAllByUserId(userId);
        request.setAttribute(SessionAttribute.REWARDS.getValue(), rewards);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.REWARDS.getPath())).include(request, response);
    }

    private void processSingleReward(HttpServletRequest request, HttpServletResponse response, String rewardId) throws ServletException, IOException {
        int id = Integer.parseInt(rewardId);
        var reward = rewardService.findById(id);

        request.setAttribute("rewardData", new JSONObject(reward));
        request.getRequestDispatcher(JspHelper.getPath("/reward-modal-window")).include(request, response);
    }

    private List<Reward> getRewardList(HttpServletRequest request) {
        var tempObject = request.getSession().getAttribute(SessionAttribute.REWARDS.getValue());
        List<Reward> rewards = new ArrayList<>();
        if (tempObject instanceof List<?> tempList) {
            if (!tempList.isEmpty() && tempList.get(0) instanceof Reward) {
                rewards = (List<Reward>) tempList;
            }
        }
        return rewards;
    }
}
