package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.UserLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<UserLocationEntity, Long> {
}
