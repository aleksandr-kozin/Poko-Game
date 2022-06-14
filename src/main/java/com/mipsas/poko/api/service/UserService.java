package com.mipsas.poko.api.service;

import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.security.jwt.JwtUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void saveUser(UserRegisterRequest request);
    JwtUser getJwtUser(String email);
    void signOut(HttpServletRequest request, HttpServletResponse response);
}
