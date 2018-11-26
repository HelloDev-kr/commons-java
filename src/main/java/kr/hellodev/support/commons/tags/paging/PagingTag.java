package kr.hellodev.support.commons.tags.paging;

import kr.hellodev.support.commons.tags.paging.type.DefaultPagingRender;
import kr.hellodev.support.commons.tags.paging.type.Type01PagingRender;
import kr.hellodev.support.commons.utils.ServletCompatibilityUtil;
import lombok.Setter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:09 PM
 */
public class PagingTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    @Setter
    private PagingInfo pagingInfo;
    @Setter
    private String type;
    @Setter
    private String jsFunction;

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            IPagingRender pagingRender;

            if ("type01".equals(type))
                pagingRender = new Type01PagingRender(ServletCompatibilityUtil.getServletInfo(pageContext.getServletContext()));
            else
                // type 설정이 없으면 DefaultPagingRender 를 사용
                pagingRender = new DefaultPagingRender();

            String contents = pagingRender.pagingRender(pagingInfo, jsFunction);
            out.println(contents);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}
