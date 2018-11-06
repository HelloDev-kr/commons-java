package kr.hellodev.support.commons.binding;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 3:17 PM
 */
public class BasePacket {

    private static final Logger REQ_DTL = LoggerFactory.getLogger("REQ_DTL");

    private static JsonSerializer<Date> dateJsonSerializer =
            (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());

    private static JsonDeserializer<Date> dateJsonDeserializer =
            (json, typeOfT, context) -> json == null ? null : new Date(json.getAsLong());

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping()
            .serializeSpecialFloatingPointValues()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(Date.class, dateJsonSerializer)
            .registerTypeAdapter(Date.class, dateJsonDeserializer)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Expose
    @Getter
    private int result;
    @Expose
    @Getter
    @Setter
    private String msg;
    @Expose
    @Getter
    @Setter
    private List<BindingError> errors;
    @Expose
    @Getter
    @Setter
    private String exception;
    @Expose
    @Getter
    @Setter
    private long responseTime;

    public void setResult(IResult result) {
        this.result = result.code();
    }

    public String toJsonBody() {
        String json = gson.toJson(this);

        // Response Log
        {
            final StringBuilder resLog = new StringBuilder();
            resLog.append("\n==========================================================================================");
            resLog.append("\nResponse - ");
            resLog.append("\n\t");
            resLog.append(json);
            resLog.append("\n==========================================================================================");
            REQ_DTL.info(resLog.toString());
        }

        return json;
    }
}
