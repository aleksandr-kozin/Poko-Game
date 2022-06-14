package com.mipsas.poko.data.repository;

import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.security.jwt.JwtUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByNickName(String nickName);

    @Query("""
            SELECT new com.mipsas.poko.security.jwt.JwtUser(u.nickName, c.password, u.role, u.status) 
            FROM CredentialEntity c JOIN c.user u
            """)
    Optional<JwtUser> findJwtUserByEmail(String email);
}
