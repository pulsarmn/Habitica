package com.pulsar.habitica.servlet;

import com.pulsar.habitica.dao.reward.RewardDaoImpl;
import com.pulsar.habitica.dto.RewardDto;
import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.entity.Reward;
import com.pulsar.habitica.filter.PrivatePaths;
import com.pulsar.habitica.service.RewardService;
import com.pulsar.habitica.util.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        List<Reward> rewards = rewardService.findAllByUserId(user.getId());

        request.setAttribute(SessionAttribute.REWARDS.getValue(), rewards);
        request.getRequestDispatcher(JspHelper.getPath(PrivatePaths.REWARDS.getPath())).include(request, response);
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

    }

    @Override
    public void destroy() {

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
