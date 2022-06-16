package com.mipsas.poko.api.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private final List<FieldError> fieldErrors;

    public ValidationErrorResponse(ErrorStatus errorStatus, List<FieldError> fieldErrors) {
        super(errorStatus);
        this.fieldErrors = fieldErrors;
    }
}
