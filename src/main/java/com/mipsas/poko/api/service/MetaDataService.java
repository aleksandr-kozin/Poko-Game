package com.mipsas.poko.api.service;

import com.mipsas.poko.data.entity.MetaDataEntity;
import javax.servlet.http.HttpServletRequest;

public interface MetaDataService {
    MetaDataEntity saveMetaData(HttpServletRequest request);
}
