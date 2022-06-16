package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.JwtBlackListEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JwtBlackListRepository extends JpaRepository<JwtBlackListEntity, Long> {

    Optional<JwtBlackListEntity> findByToken(String token);

    @Modifying
    @Query("""
            DELETE FROM JwtBlackListEntity j WHERE j.expirationDate < NOW()
            """)
    void deleteExpired();
}
