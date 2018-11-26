package kr.hellodev.support.commons.tags.string;

import lombok.Setter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:36 PM
 */
public class ArrayToStringTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    @Setter
    private List<?> list;
    @Setter
    private String delim = ",";                /* 기본 구분자 ',' */
    @Setter
    private int startWith = 0;
    @Setter
    private int endWith = Integer.MAX_VALUE;

    @Override
    public int doEndTag() throws JspException {
        // validate
        validate();

        try {
            JspWriter out = pageContext.getOut();
            StringBuilder sb = new StringBuilder();

            if (list != null && !list.isEmpty()) {
                boolean isStartWrite = false;
                for (int i = 0; i < list.size(); i++) {
                    if (i < startWith)
                        continue;
                    if (i > endWith)
                        break;
                    if (isStartWrite)
                        sb.append(delim);

                    Object o = list.get(i);
                    if (o instanceof String)
                        sb.append(o.toString());

                    isStartWrite = true;
                }
            }
            out.println(sb.toString());
        } catch (Exception e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    private void validate() throws JspException {
        if (startWith < 0)
            throw new JspException("startWith 가 0 보다 작을 수 없습니다.");
        if (startWith > endWith)
            throw new JspException("startWith 가 endWith 보다 클 수 없습니다.");
    }
}
