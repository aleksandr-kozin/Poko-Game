package com.mipsas.poko.api.exception;

import lombok.Getter;

@Getter
public class PokoGenericException extends RuntimeException {
    private final String code;

    public PokoGenericException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        code = errorStatus.name();
    }
}
