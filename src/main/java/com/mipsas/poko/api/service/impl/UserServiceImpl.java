package com.mipsas.poko.api.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.EXISTS_CREDENTIAL;
import static com.mipsas.poko.api.exception.ErrorStatus.EXISTS_USER;
import static com.mipsas.poko.api.exception.ErrorStatus.NOT_EXISTS_USER;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.service.UserService;
import static com.mipsas.poko.common.enums.UserAuthority.USER;
import static com.mipsas.poko.common.enums.UserStatus.NOT_ACTIVE;
import com.mipsas.poko.data.entity.CredentialEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.repository.CredentialRepository;
import com.mipsas.poko.data.repository.UserRepository;
import com.mipsas.poko.security.jwt.JwtUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserRegisterRequest request) {
        if (userRepository.existsByNickName(request.nickName())) {
            EXISTS_USER.throwException();
        }

        if (credentialRepository.existsByEmail(request.email())) {
            EXISTS_CREDENTIAL.throwException();
        }

        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .nickName(request.nickName())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .role(USER)
                .status(NOT_ACTIVE)
                .build());

        credentialRepository.save(CredentialEntity.builder()
                .user(savedUser)
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build());
    }

    @Override
    public JwtUser getJwtUser(String email) {
        return userRepository
                .findJwtUserByEmail(email)
                .orElseThrow(NOT_EXISTS_USER::getException);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
    }
}