package com.mipsas.poko.api.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.AsnResponse;
import com.mipsas.poko.api.model.DeviceDetails;
import com.mipsas.poko.api.service.MetaDataService;
import static com.mipsas.poko.common.utils.IpAddressUtil.extractIp;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.repository.MetaDataRepository;
import java.net.InetAddress;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import static org.springframework.http.HttpHeaders.USER_AGENT;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ua_parser.Parser;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetaDataServiceImpl implements MetaDataService {

    @Value("${geolite2.asn-db-location}")
    private String geolite2ASNDbLocation;

    private final MetaDataRepository metaDataRepository;

    @Override
    public MetaDataEntity saveMetaData(HttpServletRequest request) {
        try (DatabaseReader databaseReader = new DatabaseReader.Builder(ResourceUtils.getFile(geolite2ASNDbLocation)).build()) {
            String ip = extractIp(request);

            AsnResponse asnResponse = databaseReader.asn(InetAddress.getByName(ip));

            String provider = asnResponse.getAutonomousSystemOrganization();
            Long systemNumber = asnResponse.getAutonomousSystemNumber();

            DeviceDetails deviceDetails = getDeviceDetails(request);

            MetaDataEntity metaData = metaDataRepository.save(MetaDataEntity.builder()
                    .ip(ip)
                    .provider(provider)
                    .systemNumber(systemNumber)
                    .agentName(deviceDetails.getAgentName())
                    .agentVersion(deviceDetails.getAgentVersion())
                    .osName(deviceDetails.getOsName())
                    .osVersion(deviceDetails.getOsVersion())
                    .deviceName(deviceDetails.getDeviceName())
                    .build());

            log.info("Metadata saved successfully");

            return metaData;
        } catch (Exception e) {
            log.error("Failed to save meta data: {}", e.getMessage());
        }

        return null;
    }

    private DeviceDetails getDeviceDetails(HttpServletRequest request) {
        return Optional.of(request.getHeader(USER_AGENT))
                .map(userAgent -> new Parser().parse(userAgent))
                .map(client -> DeviceDetails.builder()
                        .agentName(client.userAgent.family)
                        .agentVersion(client.userAgent.major)
                        .osName(client.os.family)
                        .osVersion(client.os.major)
                        .deviceName(client.device.family)
                        .build())
                .orElse(new DeviceDetails());
    }
}
