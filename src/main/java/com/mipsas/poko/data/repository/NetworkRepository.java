package com.mipsas.poko.data.repository;

import com.mipsas.poko.common.enums.NetworkType;
import com.mipsas.poko.data.entity.NetworkEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<NetworkEntity, Long> {

    Optional<NetworkEntity> findByNetworkTypeAndSignalStrength(NetworkType type, Integer signalStrength);
}
