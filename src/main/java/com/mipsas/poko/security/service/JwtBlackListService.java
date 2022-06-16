package com.mipsas.poko.security.service;

import com.mipsas.poko.data.entity.JwtBlackListEntity;

public interface JwtBlackListService {
    void addToBlackList(JwtBlackListEntity jwtBlackListEntity);
    boolean isNotBlacklisted(String token);
    void deleteExpired();
}
