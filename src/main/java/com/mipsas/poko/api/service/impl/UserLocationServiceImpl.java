package com.mipsas.poko.api.service.impl;

import com.mipsas.poko.api.service.UserLocationService;
import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.data.repository.UserLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationServiceImpl implements UserLocationService {
    private final UserLocationRepository userLocationRepository;

    @Override
    public UserLocationEntity save(UserLocationEntity userLocation) {
        return userLocationRepository.save(userLocation);
    }
}
