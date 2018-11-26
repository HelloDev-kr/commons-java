package kr.hellodev.support.commons.tags.paging.type;

import kr.hellodev.support.commons.tags.paging.AbstractPagingRender;
import kr.hellodev.support.commons.tags.paging.PagingInfo;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:32 PM
 */
public class DefaultPagingRender extends AbstractPagingRender {

    public DefaultPagingRender() {
        firstPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">[처음]</a></li>\n";
        previousPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">[이전]</a></li>\n";

        currentPageLabel = "<li class=\"num\">{0}</li>\n";
        otherPageLabel = "<li class=\"num\"><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>\n";

        nextPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">[다음]</a></li>\n";
        lastPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">[마지막]</a></li>\n";
    }

    @Override
    public String pagingRender(PagingInfo pagingInfo, String jsFunction) {
        return super.pagingRender(pagingInfo, jsFunction);
    }
}
