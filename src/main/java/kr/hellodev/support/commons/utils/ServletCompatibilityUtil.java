package kr.hellodev.support.commons.utils;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:07 AM
 */

import javax.servlet.ServletContext;

/**
 * This utility helps to be compatible with Servlet API 2.4
 */
public class ServletCompatibilityUtil {

    private ServletCompatibilityUtil() {
    }

    public static String getServletInfo(ServletContext servletContext) {
        if (servletContext != null) {
            if (servletContext.getMajorVersion() >= 3 || servletContext.getMajorVersion() == 2 && servletContext.getMinorVersion() >= 5)
                return servletContext.getContextPath();
            else
                return servletContext.getServletContextName();
        } else {
            return "null";
        }
    }
}
