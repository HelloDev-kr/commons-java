package kr.hellodev.support.commons.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 4:49 PM
 */
@Getter
@Setter
public abstract class BaseModel {

    private String regUser;
    private String modUser;
    private Date regDate;
    private Date modDate;
    private boolean delete;

    /* paging */
    private boolean paging = true;
    private int currentPageNo = 1;
    private int pageSize = 10;
    private int recordCountPerPage = 10;
    private long totalRecordSize = 0;

    private int firstIndex = 1;
    private int lastIndex = 10;
    private int num = 0;

    /* sort */
    private String sortKey;
    private String sortOrder;

    /* search */
    private String searchKey;
    private String searchWord;
}
