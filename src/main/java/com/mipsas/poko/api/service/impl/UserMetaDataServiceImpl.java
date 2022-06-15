package com.mipsas.poko.api.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.AsnResponse;
import com.mipsas.poko.api.service.UserMetaDataService;
import com.mipsas.poko.api.service.UserService;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.repository.MetaDataRepository;
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
public class UserMetaDataServiceImpl implements UserMetaDataService {

    @Value("${geolite2.asn-db-location}")
    private String geolite2ASNDbLocation;

    private final UserService userService;
    private final MetaDataRepository metaDataRepository;

    @Override
    public void verifyUserMetaData(HttpServletRequest request) {
        Optional.ofNullable(userService.getAuthenticatedUser())
                .ifPresent(user -> {
                    try (DatabaseReader databaseReader = new DatabaseReader.Builder(ResourceUtils.getFile(geolite2ASNDbLocation)).build()) {
//            String ip = extractIp(request);
                        String ip = "78.56.193.46"; // test ip

                        AsnResponse asnResponse = databaseReader.asn(InetAddress.getByName(ip));

                        String provider = asnResponse.getAutonomousSystemOrganization();
                        Long systemNumber = asnResponse.getAutonomousSystemNumber();

                        metaDataRepository.save(MetaDataEntity.builder()
                                .user(user)
                                .ip(ip)
                                .provider(provider)
                                .systemNumber(systemNumber)
                                .build());

                    } catch (Exception e) {
                        log.error("Failed to verified user meta data: {}", e.getMessage());
                    }
                });
    }
}
