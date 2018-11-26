package kr.hellodev.support.commons.tags.paging.service;

import kr.hellodev.support.commons.model.BaseModel;
import kr.hellodev.support.commons.tags.paging.PagingInfo;
import org.springframework.stereotype.Service;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 5:34 PM
 */
@Service
public class PagingService implements IPagingService {

    @Override
    public PagingInfo getPagingInfo(BaseModel model) {
        return new PagingInfo(model);
    }

    @Override
    public PagingInfo getPagingInfo(BaseModel model, int recordCountPerPage, int pageSize) {
        model.setRecordCountPerPage(recordCountPerPage);
        model.setPageSize(pageSize);

        return new PagingInfo(model);
    }
}
