package kr.hellodev.support.commons.tags.paging.type;

import kr.hellodev.support.commons.tags.paging.AbstractPagingRender;
import kr.hellodev.support.commons.tags.paging.PagingInfo;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:33 PM
 */
public class Type01PagingRender extends AbstractPagingRender {

    public Type01PagingRender(String contextPath) {
        firstPageLabel = "<a href=\"#\" class=\"mov_box prev10\" onclick=\"{0}({1});return false;\"><span>10페이지 이전 목록</span></a>\n";
        previousPageLabel = "<a href=\"#\" class=\"mov_box prev1\" onclick=\"{0}({1});return false;\"><span>1페이지 이전 목록</span></a>\n";

        currentPageLabel = "<a href=\"#\" class=\"num_box on\" onclick=\"return false;\">{0}</a>\n";
        otherPageLabel = "<a href=\"#\" class=\"num_box\" onclick=\"{0}({1});return false;\">{2}</a>\n";

        nextPageLabel = "<a href=\"#\" class=\"mov_box next1\" onclick=\"{0}({1});return false;\"><span>1페이지 다음 목록</span></a>\n";
        lastPageLabel = "<a href=\"#\" class=\"mov_box next10\" onclick=\"{0}({1});return false;\"><span>10페이지 다음 목록</span></a>\n";
    }

    @Override
    public String pagingRender(PagingInfo pagingInfo, String jsFunction) {
        return super.pagingRender(pagingInfo, jsFunction);
    }
}
