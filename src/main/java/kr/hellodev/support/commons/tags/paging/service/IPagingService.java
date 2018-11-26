package kr.hellodev.support.commons.tags.paging.service;

import kr.hellodev.support.commons.model.BaseModel;
import kr.hellodev.support.commons.tags.paging.PagingInfo;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:33 PM
 */
public interface IPagingService {

    PagingInfo getPagingInfo(BaseModel model);

    PagingInfo getPagingInfo(BaseModel model, int recordCountPerPage, int pageSize);
}
