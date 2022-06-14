package com.mipsas.poko.api.model.request;

import com.mipsas.poko.api.validation.Email;
import com.mipsas.poko.api.validation.Password;

public record SignInRequest(
        @Email
        String email,

        @Password
        String password
) {}
