package com.mipsas.poko.api.service;

import com.mipsas.poko.api.model.request.ResetPasswordRequest;
import com.mipsas.poko.api.model.request.SignInRequest;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.model.response.SignInResponse;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    SignInResponse signIn(SignInRequest request);
    void registerUser(UserRegisterRequest request);
    void signOut(HttpServletRequest request);
    void resetPassword(ResetPasswordRequest request);
}
