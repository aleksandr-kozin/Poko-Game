package com.mipsas.poko.api.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.mipsas.poko.api.service.UserLocationService;
import com.mipsas.poko.api.service.UserService;
import static com.mipsas.poko.common.utils.IpAddressUtil.extractIp;
import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.data.repository.UserLocationRepository;
import java.net.InetAddress;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLocationServiceImpl implements UserLocationService {

    @Value("${geolite2.city-db-location}")
    private String geoLite2CityDbLocation;

    private final UserService userService;
    private final UserLocationRepository userLocationRepository;

    @Override
    public void verifyUserLocation(HttpServletRequest request) {
        Optional.ofNullable(userService.getAuthenticatedUser())
                .ifPresent(user -> {
                    try (DatabaseReader databaseReader = new DatabaseReader.Builder(ResourceUtils.getFile(geoLite2CityDbLocation)).build()) {
                        String ip = extractIp(request);

                        CityResponse cityResponse = databaseReader.city(InetAddress.getByName(ip));

                        String country = cityResponse.getCountry().getName();
                        String city = cityResponse.getCity().getName();
                        String postal = cityResponse.getPostal().getCode();
                        String state = cityResponse.getLeastSpecificSubdivision().getName();
                        Double latitude = cityResponse.getLocation().getLatitude();
                        Double longitude = cityResponse.getLocation().getLongitude();

                        userLocationRepository.save(UserLocationEntity.builder()
                                .user(user)
                                .country(country)
                                .city(city)
                                .longitude(longitude)
                                .latitude(latitude)
                                .postal(postal)
                                .state(state)
                                .build());

                        log.info("User's {} location saved successfully", user.getNickName());

                    } catch (Exception e) {
                        log.error("Failed to verified user location: {}", e.getMessage());
                    }
                });
    }
}
