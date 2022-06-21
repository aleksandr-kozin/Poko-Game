package com.mipsas.poko.api.service;

import com.mipsas.poko.api.model.request.UpdateUserRequest;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.security.jwt.JwtUser;
import java.util.List;

public interface UserService {
    UserEntity getUserById(Long id);
    void updateUser(UpdateUserRequest request);
    List<UserLocationEntity> getUserLocations(Long id);
    List<MetaDataEntity> getUserMetaData(Long id);
    JwtUser getJwtUser(String email);
    UserEntity getAuthenticatedUser();
}
