package com.mipsas.poko.api.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.NOT_EXIST_CREDENTIAL;
import com.mipsas.poko.api.service.CredentialService;
import com.mipsas.poko.data.entity.CredentialEntity;
import com.mipsas.poko.data.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepository credentialRepository;

    @Override
    public CredentialEntity getByEmail(String email) {
        return credentialRepository.findByEmail(email)
                .orElseThrow(NOT_EXIST_CREDENTIAL::getException);
    }
}
