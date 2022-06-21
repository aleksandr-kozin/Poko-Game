package com.mipsas.poko.api.service;

import com.mipsas.poko.data.entity.LocationEntity;
import javax.servlet.http.HttpServletRequest;

public interface LocationService {
    LocationEntity saveLocation(HttpServletRequest request);
    LocationEntity getLocation(HttpServletRequest request);
}
