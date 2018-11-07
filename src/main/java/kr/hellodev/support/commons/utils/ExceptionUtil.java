package kr.hellodev.support.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 10:50 AM
 */
public class ExceptionUtil {

    public static String traceException(Exception e) {
        String result = null;
        ByteArrayOutputStream stream = null;
        PrintWriter writer = null;

        try {
            stream = new ByteArrayOutputStream();
            writer = new PrintWriter(stream);

            e.printStackTrace(writer);
            writer.flush();

            result = new String(stream.toByteArray());
        } catch (Exception e1) {
            result = "Cannot catch exception.";
        } finally {
            Util.closeQuietly(stream);
            Util.closeQuietly(writer);
        }

        // 길이 제한
        //if(result.length() > 1900)
        //	result = result.substring(0, 1900) + "...";

        return result;
    }
}
