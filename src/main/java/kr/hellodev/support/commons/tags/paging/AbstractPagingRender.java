package kr.hellodev.support.commons.tags.paging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:30 PM
 */
public abstract class AbstractPagingRender implements IPagingRender {

    private static Logger logger = LoggerFactory.getLogger(AbstractPagingRender.class);

    protected String firstPageLabel;
    protected String previousPageLabel;
    protected String currentPageLabel;
    protected String otherPageLabel;
    protected String nextPageLabel;
    protected String lastPageLabel;

    @Override
    public String pagingRender(PagingInfo pagingInfo, String jsFunction) {
        StringBuilder sb = new StringBuilder();

        int firstPageNo = pagingInfo.getFirstPageNo();
        long firstPageNoOnPageList = pagingInfo.getFirstPageNoOnPageList();
        int totalPageSize = pagingInfo.getTotalPageSize();
        int pageSize = pagingInfo.getPageSize();
        long lastPageNoOnPageList = pagingInfo.getLastPageNoOnPageList();
        int currentPageNo = pagingInfo.getCurrentPageNo();
        int lastPageNo = pagingInfo.getLastPageNo();

        logger.debug("Paging Info: totalPageSize[{}], pageSize[{}], firstPageNo[{}], currentPageNo[{}]", totalPageSize, pageSize, firstPageNo, currentPageNo);

        if (totalPageSize > pageSize) {
            if (firstPageNoOnPageList > pageSize) {
                sb.append(MessageFormat.format(firstPageLabel, jsFunction, String.valueOf(firstPageNo)));
                sb.append(MessageFormat.format(previousPageLabel, jsFunction, String.valueOf(firstPageNoOnPageList - 1)));
            }
        }

        for (long i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
            if (i == currentPageNo)
                sb.append(MessageFormat.format(currentPageLabel, String.valueOf(i)));
            else
                sb.append(MessageFormat.format(otherPageLabel, jsFunction, String.valueOf(i), String.valueOf(i)));
        }

        if (totalPageSize > pageSize) {
            if (lastPageNoOnPageList < totalPageSize) {
                sb.append(MessageFormat.format(nextPageLabel, jsFunction, String.valueOf(firstPageNoOnPageList + pageSize)));
                sb.append(MessageFormat.format(lastPageLabel, jsFunction, String.valueOf(lastPageNo)));
            }
        }

        return sb.toString();
    }
}
