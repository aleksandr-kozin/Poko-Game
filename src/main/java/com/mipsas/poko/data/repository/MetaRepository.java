package com.mipsas.poko.data.repository;


import com.mipsas.poko.data.entity.MetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetaRepository extends JpaRepository<MetaDataEntity, Long> {
}
