package kr.hellodev.support.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 5:43 PM
 */
class TokenUtil {

    static void set(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        long currentTimeMillis = System.currentTimeMillis();
        byte[] time = Long.toString(currentTimeMillis).getBytes();
        byte[] id = session.getId().getBytes();

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(id);
            md5.update(time);
            String token = toHex(md5.digest());
            request.setAttribute("token", token);
            session.setAttribute("token", token);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Unable to calculate MD5 Digests.");
        }
    }

    static String get(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return (String) session.getAttribute("token");
    }

    static boolean isValid(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String requestToken = request.getParameter("token");
        String sessionToken = (String) session.getAttribute("token");

        if (requestToken == null || sessionToken == null)
            return false;

        return requestToken.equals(sessionToken);
    }

    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte aDigest : digest) {
            sb.append(Integer.toHexString((int) aDigest & 0x00FF));
        }
        return sb.toString();
    }
}
