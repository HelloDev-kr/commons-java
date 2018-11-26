package kr.hellodev.support.commons.fcm;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FCMResponse {
    HttpsURLConnection connection;

    public FCMResponse(HttpsURLConnection connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return String.format("Response: %d\nMessage: '%s'", getResponseCode(), getResponseMessage());
    }

    public int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public String getResponseMessage() {
        InputStream in = connection.getErrorStream();

        if (in == null)
            return "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
