package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.LOCATIONS;
import com.mipsas.poko.api.model.request.LocationRequest;
import com.mipsas.poko.api.service.*;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.entity.NetworkEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.entity.UserLocationEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Locations")
@RestController
@RequestMapping(LOCATIONS)
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final NetworkService networkService;
    private final UserService userService;
    private final UserLocationService userLocationService;
    private final MetaDataService metaDataService;

    @Operation(summary = "Set user location")
    @PostMapping
    public void setLocation(@RequestBody @Valid LocationRequest locationRequest, HttpServletRequest httpRequest) {
        Optional.ofNullable(locationService.saveLocation(httpRequest))
                .ifPresent(location -> {
                    UserEntity user = userService.getAuthenticatedUser();

                    NetworkEntity network = locationRequest.toLocationNetworkEntity();
                    network.setLocation(location);
                    network = networkService.saveNetwork(network);

                    MetaDataEntity metaData = metaDataService.saveMetaData(httpRequest);

                    userLocationService.save(UserLocationEntity.builder()
                            .user(user)
                            .location(location)
                            .locationNetwork(network)
                            .metaData(metaData)
                            .build());
                });
    }
}
