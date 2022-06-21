package com.mipsas.poko.api.service.impl;

import com.mipsas.poko.api.model.request.NetworkInfoRequest;
import com.mipsas.poko.api.service.NetworkService;
import com.mipsas.poko.api.service.UserService;
import com.mipsas.poko.data.entity.NetworkInfoEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.repository.NetworkInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NetworkServiceImpl implements NetworkService {
    private final UserService userService;
    private final NetworkInfoRepository networkInfoRepository;

    @Override
    public void saveNetworkInfo(NetworkInfoRequest request) {
        UserEntity user = userService.getUserById(request.userId());
        NetworkInfoEntity networkConnection = request.toNetworkConnectionEntity();
        networkConnection.setUser(user);
        networkInfoRepository.save(networkConnection);
    }
}
