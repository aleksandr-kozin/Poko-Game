package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
}
