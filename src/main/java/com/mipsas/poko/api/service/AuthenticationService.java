package com.mipsas.poko.api.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    void signOut(HttpServletRequest request);
}
