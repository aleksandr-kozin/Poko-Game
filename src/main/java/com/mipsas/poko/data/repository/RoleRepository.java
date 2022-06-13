package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Byte> {
}
