package kr.hellodev.support.commons.fcm;

import kr.hellodev.support.commons.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.Date;

/**
 * This method allows for simple and modular Notification creation.
 * Notification can then be pushed to clients over FCM using the push() method.
 *
 * @author Raudius
 */
public class FCMPush extends Notification {
    private static final Logger LOG = LoggerFactory.getLogger("PUSH");

    private final String API_URL = "https://fcm.googleapis.com/fcm/send";
    private final String FIREBASE_SERVER_KEY;

    public FCMPush(String firebaseServerKey) {
        super();
        this.FIREBASE_SERVER_KEY = firebaseServerKey;
    }

    /**
     * Messages sent to targets.
     * This class interfaces with the FCM server by sending the Notification over HTTP-POST JSON.
     *
     * @return
     */
    public FCMResponse push() {
        LOG.debug("{} {}", new Date(), toJSON());

        HttpsURLConnection con = null;
        try {
            URL url = new URL(API_URL);
            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            // Set POST headers
            con.setRequestProperty(HttpHeaders.AUTHORIZATION, "key=" + FIREBASE_SERVER_KEY);
            con.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            // Send POST body
            con.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            dos.writeBytes(this.toJSON());
            dos.flush();
            dos.close();

            con.getResponseCode();
        } catch (Exception e) {
            LOG.error(ExceptionUtil.traceException(e));
            e.printStackTrace();
        }

        return new FCMResponse(con);
    }
}
