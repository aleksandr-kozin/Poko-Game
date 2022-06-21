package com.mipsas.poko.api.model.request;

import com.mipsas.poko.api.validation.Password;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ResetPasswordRequest(

        @NotNull(message = "{validation.notnull}")
        @Positive(message = "{validation.positive}")
        Long userId,

        @Password
        String oldPassword,

        @Password
        String newPassword,

        @Password
        String repeatPassword
) {}
