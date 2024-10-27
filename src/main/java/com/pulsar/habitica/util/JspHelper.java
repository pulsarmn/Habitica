package com.pulsar.habitica.util;

public final class JspHelper {

    private static final String JSP_FORMAT = "/WEB-INF/jsp%s.jsp";

    private JspHelper() {}

    public static String getPath(String jsp) {
        if (jsp.startsWith("/")) {
            return JSP_FORMAT.formatted(jsp);
        }
        return JSP_FORMAT.formatted("/" + jsp);
    }
}
