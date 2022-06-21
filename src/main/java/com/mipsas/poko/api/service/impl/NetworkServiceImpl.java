package com.mipsas.poko.api.service.impl;

import com.mipsas.poko.api.service.NetworkService;
import com.mipsas.poko.data.entity.NetworkEntity;
import com.mipsas.poko.data.repository.NetworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkServiceImpl implements NetworkService {
    private final NetworkRepository networkRepository;

    @Override
    public NetworkEntity saveNetwork(NetworkEntity locationNetwork) {
        return networkRepository.save(locationNetwork);
    }
}
