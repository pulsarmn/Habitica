package com.pulsar.habitica.util;

public final class JspHelper {

    private static final String JSP_FORMAT = "/WEB-INF/jsp%s.jsp";

    private JspHelper() {}

    public static String getPath(String jsp) {
        return JSP_FORMAT.formatted(jsp);
    }
}
