package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.LOCATIONS;
import com.mipsas.poko.api.model.request.LocationRequest;
import com.mipsas.poko.api.service.NetworkService;
import com.mipsas.poko.api.service.UserLocationService;
import com.mipsas.poko.data.entity.LocationNetworkEntity;
import com.mipsas.poko.data.entity.LocationEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final UserLocationService userLocationService;
    private final NetworkService networkService;

    @Operation(summary = "Set user location")
    @PostMapping
    public void setLocation(@RequestBody @Valid LocationRequest request) {
        LocationEntity userLocation = userLocationService.saveOrUpdate(request.toUserLocationEntity());

        LocationNetworkEntity locationNetwork = request.toLocationNetworkEntity();
        locationNetwork.setLocation(userLocation);

        networkService.saveNetwork(locationNetwork);
    }
}
