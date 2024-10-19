package com.pulsar.habitica.util;

import com.pulsar.habitica.annotation.UtilTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JspHelperTest {

    @UtilTest
    void testGettingPathWithBackSlash() {
        String url = "/login";
        String expected = "/WEB-INF/jsp/login.jsp";

        assertThat(JspHelper.getPath(url)).isEqualTo(expected);
    }

    @UtilTest
    void testGettingPathWithoutBackSlash() {
        String url = "login";
        String expected = "/WEB-INF/jsp/login.jsp";

        assertThat(JspHelper.getPath(url)).isEqualTo(expected);
    }
}
