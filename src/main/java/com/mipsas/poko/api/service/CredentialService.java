package com.mipsas.poko.api.service;

import com.mipsas.poko.data.entity.CredentialEntity;

public interface CredentialService {
    
    CredentialEntity getByEmail(String email);
}
