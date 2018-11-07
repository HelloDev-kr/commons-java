package kr.hellodev.support.commons.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 10:50 AM
 */
public class Util {
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException ignore) {
        }
    }

    public static String getIp(HttpServletRequest request) {
        String ip_address = request.getHeader("X-Forwarded-For");

        if (StringUtil.isEmpty(ip_address))
            ip_address = request.getRemoteAddr();

        return ip_address;
    }

    /**
     * 로그인 인증 login_certk 생성
     *
     * @return
     */
    public static String createLoginCertk() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String GenerateUUID() {

        SecureRandom prng;
        String randomNum;
        byte[] result = null;

        try {
            prng = SecureRandom.getInstance("SHA1PRNG");
            randomNum = Integer.toString(prng.nextInt());
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            result = sha.digest(randomNum.getBytes());
        } catch (Exception ignore) {
        }
        return hexEncode(result);
    }

    public static String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        for (byte b : aInput) {
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }
}
