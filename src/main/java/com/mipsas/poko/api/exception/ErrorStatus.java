package com.mipsas.poko.api.exception;

import static lombok.AccessLevel.PRIVATE;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum ErrorStatus {
    INCORRECT_REQUEST("Request didn't pass validation"),
    INCORRECT_STATUS("You cannot perform this operation."),
    INCORRECT_PASSWORD("Old password is incorrect"),
    PASSWORDS_NOT_EQUAL("Passwords are not equal"),
    INTERNAL_ERROR("Internal error"),
    UNEXPECTED_ERROR("Unexpected exception"),
    ACCESS_DENIED("You cannot perform this operation."),
    EXISTS_USER("The user already exists"),
    EXISTS_CREDENTIAL("Your email is already registered. Please login using this email."),
    EXISTS_NICKNAME("The nick name already exists."),
    NOT_EXISTS_USER("This user doesn't exist."),
    NOT_EXIST_CREDENTIAL("Credential doesn't exist"),
    NO_AUTHORIZED_USER("No authorized user"),
    BAD_CREDENTIALS("Unable to parse credentials"),
    USER_DISABLED("User is disabled"),
    USER_LOCKED("User is locked"),;

    private final String message;

    public final PokoGenericException getException() {
        return new PokoGenericException(this);
    }

    public void throwException() {
        throw new PokoGenericException(this);
    }
}
