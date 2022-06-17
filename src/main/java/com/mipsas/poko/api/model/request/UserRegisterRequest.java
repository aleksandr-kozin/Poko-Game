package com.mipsas.poko.api.model.request;

import com.mipsas.poko.api.validation.Email;
import com.mipsas.poko.api.validation.Password;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank(message = "{validation.notblank}")
        @Size(min = 2, max = 20, message = "{validation.size}")
        String nickName,

        @Size(min = 1, max = 100, message = "{validation.size}")
        String firstName,

        @Size(min = 1, max = 100, message = "{validation.size}")
        String lastName,

        @Email
        String email,

        @Password
        String password
) {
}
