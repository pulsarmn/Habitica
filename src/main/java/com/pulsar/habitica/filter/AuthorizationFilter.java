package com.pulsar.habitica.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var session = httpRequest.getSession(false);

        boolean isLogged = (session != null && session.getAttribute("user") != null);

        String requestURI = httpRequest.getRequestURI();
        if (isLogged && PagePaths.getGuestPaths().contains(requestURI)) {
            httpResponse.sendRedirect(PrivatePaths.HOME.getPath());
        }else if ((!isLogged && PagePaths.getPrivatePaths().contains(requestURI)) || requestURI.equals("/")) {
            httpResponse.sendRedirect(GuestPaths.LOGIN.getPath());
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
