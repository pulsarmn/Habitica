package com.pulsar.habitica.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JspHelperTest {

    @Test
    void testGettingPathWithBackSlash() {
        String url = "/login";
        String expected = "/WEB-INF/jsp/login.jsp";

        assertThat(JspHelper.getPath(url)).isEqualTo(expected);
    }

    @Test
    void testGettingPathWithoutBackSlash() {
        String url = "login";
        String expected = "/WEB-INF/jsp/login.jsp";

        assertThat(JspHelper.getPath(url)).isEqualTo(expected);
    }
}
