package com.mipsas.poko.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mipsas.poko.api.validation.Enum;
import com.mipsas.poko.common.enums.NetworkType;
import com.mipsas.poko.data.entity.NetworkInfoEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record NetworkInfoRequest(

        @NotNull(message = "{validation.notnull}")
        @Positive(message = "{validation.positive}")
        Long userId,

        @NotNull(message = "{validation.notnull}")
        @Enum(enumClass = NetworkType.class)
        NetworkType networkType,

        @NotNull(message = "{validation.notnull}")
        @Positive(message = "{validation.positive}")
        Integer signalStrength
) {
        @JsonIgnore
        public NetworkInfoEntity toNetworkConnectionEntity() {
                return NetworkInfoEntity.builder()
                        .networkType(networkType)
                        .signalStrength(signalStrength)
                        .build();
        }
}
