package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.CredentialEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

    Optional<CredentialEntity> findByEmail(String email);
    Optional<CredentialEntity> findByUserId(Long userId);
}
