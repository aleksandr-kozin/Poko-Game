package com.mipsas.poko.api.service;

import com.mipsas.poko.data.entity.CredentialEntity;

public interface CredentialService {

    CredentialEntity save(CredentialEntity credential);
    CredentialEntity getByUserId(Long userId);
    CredentialEntity getByEmail(String email);
    void throwExceptionIfExists(String email);
}
