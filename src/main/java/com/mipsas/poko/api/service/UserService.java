package com.mipsas.poko.api.service;

import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.security.jwt.JwtUser;

public interface UserService {
    void registerUser(UserRegisterRequest request);
    JwtUser getJwtUser(String email);
    UserEntity getAuthenticatedUser();
}
