package com.mipsas.poko.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mipsas.poko.api.validation.Enum;
import com.mipsas.poko.common.enums.NetworkType;
import com.mipsas.poko.data.entity.LocationNetworkEntity;
import com.mipsas.poko.data.entity.LocationEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record LocationRequest(
        @NotNull(message = "{validation.notnull}")
        Double latitude,

        @NotNull(message = "{validation.notnull}")
        Double longitude,

        @NotNull(message = "{validation.notnull}")
        @Enum(enumClass = NetworkType.class)
        String networkType,

        @NotNull(message = "{validation.notnull}")
        @Positive(message = "{validation.positive}")
        Integer signalStrength

        // ...
) {
    @JsonIgnore
    public LocationNetworkEntity toLocationNetworkEntity() {
        return LocationNetworkEntity.builder()
                .networkType(NetworkType.valueOf(networkType))
                .signalStrength(signalStrength)
                .build();
    }

    @JsonIgnore
    public LocationEntity toUserLocationEntity() {
        return LocationEntity.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
