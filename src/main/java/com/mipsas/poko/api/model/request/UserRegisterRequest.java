package com.mipsas.poko.api.model.request;

import com.mipsas.poko.api.validation.Email;
import com.mipsas.poko.api.validation.Password;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank
        @Size(min = 2, max = 20)
        String nickName,

        String firstName,
        String lastName,

        @Email
        String email,

        @Password
        String password
) {
}
