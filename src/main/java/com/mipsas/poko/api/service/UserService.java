package com.mipsas.poko.api.service;

import com.mipsas.poko.api.model.request.UpdateUserRequest;
import com.mipsas.poko.common.enums.UserStatus;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.entity.LocationEntity;
import com.mipsas.poko.security.jwt.JwtUser;
import java.util.List;

public interface UserService {
    UserEntity save(UserEntity user);
    UserEntity getUserById(Long id);
    void updateUser(UpdateUserRequest request);
    List<LocationEntity> getUserLocations(Long id);
    List<MetaDataEntity> getUserMetaData(Long id);
    JwtUser getJwtUser(String email);
    UserEntity getAuthenticatedUser();
    void throwExceptionIfExists(String nickName);
    void changeUserStatus(String nickName, UserStatus status);
}
