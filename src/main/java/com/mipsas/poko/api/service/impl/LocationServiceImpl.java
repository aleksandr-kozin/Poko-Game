package com.mipsas.poko.api.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.mipsas.poko.api.service.LocationService;
import static com.mipsas.poko.common.utils.IpAddressUtil.extractIp;
import com.mipsas.poko.data.entity.LocationEntity;
import com.mipsas.poko.data.repository.LocationRepository;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    @Value("${geolite2.city-db-location}")
    private String geoLite2CityDbLocation;

    private final LocationRepository locationRepository;

    @Override
    public LocationEntity saveLocation(HttpServletRequest request) {
        try {
            LocationEntity location = getLocation(request);

            locationRepository
                    .findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude())
                    .ifPresent(l -> location.setId(l.getId()));

            LocationEntity savedLocation = locationRepository.save(location);

            log.info("Location latitude: {}, longitude: {} saved successfully",
                    savedLocation.getLatitude(), savedLocation.getLongitude());

            return savedLocation;
        } catch (Exception e) {
            log.error("Failed to verified user location: {}", e.getMessage());
        }

        return null;
    }

    @Override
    @SneakyThrows
    public LocationEntity getLocation(HttpServletRequest request) {
        try (DatabaseReader databaseReader = new DatabaseReader.Builder(ResourceUtils.getFile(geoLite2CityDbLocation)).build()) {
            String ip = extractIp(request);

            CityResponse cityResponse = databaseReader.city(InetAddress.getByName(ip));

            String country = cityResponse.getCountry().getName();
            String city = cityResponse.getCity().getName();
            String postal = cityResponse.getPostal().getCode();
            String state = cityResponse.getLeastSpecificSubdivision().getName();
            Double latitude = cityResponse.getLocation().getLatitude();
            Double longitude = cityResponse.getLocation().getLongitude();

            return LocationEntity.builder()
                    .country(country)
                    .city(city)
                    .longitude(longitude)
                    .latitude(latitude)
                    .postal(postal)
                    .state(state)
                    .build();
        }
    }
}
