package com.mipsas.poko.security.service.impl;

import com.mipsas.poko.data.entity.JwtBlackListEntity;
import com.mipsas.poko.data.repository.JwtBlackListRepository;
import com.mipsas.poko.security.service.JwtBlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtBlackListServiceImpl implements JwtBlackListService {
    private final JwtBlackListRepository jwtBlackListRepository;

    @Override
    public void addToBlackList(JwtBlackListEntity jwtBlackListEntity) {
        jwtBlackListRepository.save(jwtBlackListEntity);
    }

    @Override
    public boolean isNotBlacklisted(String token) {
        return jwtBlackListRepository.findByToken(token).isEmpty();
    }

    @Override
    public void deleteExpired() {
        jwtBlackListRepository.deleteExpired();
    }
}
