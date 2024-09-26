package com.pulsar.habitica.util;

import com.pulsar.habitica.dto.UserDto;
import com.pulsar.habitica.exception.UnauthorizedException;
import com.pulsar.habitica.servlet.SessionAttribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class ServletUtil {

    private ServletUtil() {}

    public static UserDto getAuthenticatedUser(HttpServletRequest request) {
        var user = (UserDto) request.getSession().getAttribute(SessionAttribute.USER.getValue());
        if (user == null) {
            throw new UnauthorizedException();
        }
        return user;
    }

    public static JSONObject getJson(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (var bufferedReader = request.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        return new JSONObject(sb.toString());
    }
}
