package kr.hellodev.support.commons.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:07 AM
 */
public class LogUtil {

    public static String getParameter(HttpServletRequest request) {
        return getParameter(request, null);
    }

    public static String getParameter(HttpServletRequest request, String[] maskParams) {
        StringBuffer log = new StringBuffer();
        log.append("parameter=\n");

        for (Enumeration<String> enumeration = request.getParameterNames(); enumeration.hasMoreElements(); ) {
            String name = enumeration.nextElement();
            String value = "";
            String[] values = request.getParameterValues(name);

            if (values != null && values.length > 0) {
                if (maskParams != null && StringUtil.match(maskParams, name)) {
                    value += "**********";
                } else
                    for (int i = 0; i < values.length; ++i) {
                        if (i == 0)
                            value += values[i];
                        else
                            value += "," + values[i];
                    }
            }
            log.append("\t").append(name).append("=").append(value).append("\n");
        }
        return log.toString();
    }
}
