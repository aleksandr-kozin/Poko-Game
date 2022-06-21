package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.LocationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
