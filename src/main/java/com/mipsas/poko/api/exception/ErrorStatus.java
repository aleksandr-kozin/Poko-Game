package com.mipsas.poko.api.exception;

import static lombok.AccessLevel.PRIVATE;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum ErrorStatus {
    INCORRECT_REQUEST("Request didn't pass validation"),
    INTERNAL_ERROR("Internal error"),
    UNEXPECTED_ERROR("Unexpected exception"),
    ACCESS_DENIED("You cannot perform this operation."),
    NOT_EXISTS_USER("This user doesn't exist."),
    NOT_EXIST_CREDENTIAL("Credential doesn't exist"),
    BAD_CREDENTIALS("Unable to parse credentials");

    private final String message;

    public final PokoGenericException getException() {
        return new PokoGenericException(this);
    }

    public void throwException() {
        throw new PokoGenericException(this);
    }
}
