package com.mipsas.poko.api.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record UpdateUserRequest(

        @NotNull(message = "{validation.notnull}")
        @Positive(message = "{validation.positive}")
        Long id,

        @Size(min = 1, max = 100, message = "{validation.size}")
        String firstName,

        @Size(min = 1, max = 100, message = "{validation.size}")
        String lastName
        //...
) {
}
