package com.mipsas.poko.common.enums;

public enum UserStatus {
    ACTIVE, NOT_ACTIVE, DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isNotDeleted() {
        return this != DELETED;
    }
}
