package kr.hellodev.support.commons.tags.paging;

import kr.hellodev.support.commons.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:04 PM
 */
public class PagingInfo {

    private final static int FIRST_PAGE_NO = 1;    /* 첫 페이지는 1 */

    /* Required Fields */
    @Getter
    @Setter
    private int currentPageNo;
    @Getter
    @Setter
    private int recordCountPerPage;
    @Getter
    @Setter
    private int pageSize;
    @Getter
    @Setter
    private long totalRecordSize;

    /* Not Required Fields */
    @Setter
    private int totalPageSize;
    @Setter
    private long firstPageNoOnPageList;
    @Setter
    private long lastPageNoOnPageList;
    @Setter
    private int firstRecordIndex;
    @Setter
    private int lastRecordIndex;

    public PagingInfo(BaseModel model) {
        this.currentPageNo = model.getCurrentPageNo();
        this.recordCountPerPage = model.getRecordCountPerPage();
        this.pageSize = model.getPageSize();
        this.totalRecordSize = model.getTotalRecordSize();

        model.setFirstIndex(getFirstRecordIndex());
        model.setLastIndex(getLastRecordIndex());
    }

    public int getFirstPageNo() {
        return FIRST_PAGE_NO;
    }

    public int getLastPageNo() {
        return getTotalPageSize();
    }

    public int getTotalPageSize() {
        totalPageSize = (int) (((getTotalRecordSize() - 1) / getRecordCountPerPage()) + 1);
        return totalPageSize;
    }

    public long getFirstPageNoOnPageList() {
        firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
        return firstPageNoOnPageList;
    }

    public long getLastPageNoOnPageList() {
        lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
        if (lastPageNoOnPageList > getTotalPageSize())
            lastPageNoOnPageList = getTotalPageSize();
        return lastPageNoOnPageList;
    }

    public int getFirstRecordIndex() {
        firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
        return firstRecordIndex;
    }

    public int getLastRecordIndex() {
        lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
        return lastRecordIndex;
    }

    /**
     * 목록 페이지에서 NO 숫자 표기
     *
     * @param num
     * @return
     */
    public int getNum(int num) {
        return (int) ((totalRecordSize - (currentPageNo - 1) * recordCountPerPage) - num + 1);
    }

    /**
     * 목록 페이지에서 NO 숫자표기, jdk 1.5에서 getNum(int) 호출 못하는 문제로 메소드 추가
     *
     * @return
     */
    public int getListNo() {
        return (int) ((totalRecordSize - (currentPageNo - 1) * recordCountPerPage) + 1);
    }
}
