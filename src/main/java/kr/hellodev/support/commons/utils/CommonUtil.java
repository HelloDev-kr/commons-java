package kr.hellodev.support.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 3:35 PM
 */
public class CommonUtil {

    /**
     * Token set
     *
     * @param request {@link HttpServletRequest}
     */
    private static void setToken(HttpServletRequest request) {
        TokenUtil.set(request);
    }

    /**
     * Token get
     *
     * @param request {@link HttpServletRequest}
     * @return {@link String}
     */
    public static String getToken(HttpServletRequest request) {
        return TokenUtil.get(request);
    }

    /**
     * 새로고침 방지
     *
     * @param request {@link HttpServletRequest}
     * @return boolean
     */
    public static boolean isValidToken(HttpServletRequest request) {
        if (!TokenUtil.isValid(request))
            return false;
        setToken(request);
        return true;
    }

    /**
     * Disposition 지정하기
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param filename {@link String}
     * @throws IOException IOException
     */
    public static void setDisposition(HttpServletRequest request, HttpServletResponse response, String filename) throws IOException {
        String browser = getBrowser(request);
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename;

        if ("MSIE".equals(browser))
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        else if ("Firefox".equals(browser) || "Opera".equals(browser))
            encodedFilename = "\"" + new String(filename.getBytes(StandardCharsets.UTF_8), "8859_1") + "\"";
        else if ("Chrome".equals(browser)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~')
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                else
                    sb.append(c);
            }
            encodedFilename = sb.toString();
        } else
            throw new IOException("Not supported browser!");

        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

        if ("Opera".equals(browser))
            response.setContentType("application/octet-stream);charset=UTF-8");
    }

    /**
     * 브라우저 구분 얻기
     *
     * @param request {@link HttpServletRequest}
     * @return {@link String}
     */
    private static String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.contains("MSIE"))
            return "MSIE";
        else if (header.contains("Chrome"))
            return "Chrome";
        else if (header.contains("Opera"))
            return "Opera";
        return "Firefox";
    }
}
