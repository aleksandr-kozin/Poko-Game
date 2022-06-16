package com.mipsas.poko.api.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.EXISTS_CREDENTIAL;
import static com.mipsas.poko.api.exception.ErrorStatus.EXISTS_USER;
import static com.mipsas.poko.api.exception.ErrorStatus.NOT_EXISTS_USER;
import com.mipsas.poko.api.model.request.UpdateUserRequest;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.service.UserService;
import static com.mipsas.poko.common.enums.UserAuthority.USER;
import static com.mipsas.poko.common.enums.UserStatus.DELETED;
import static com.mipsas.poko.common.enums.UserStatus.NOT_ACTIVE;
import com.mipsas.poko.data.entity.CredentialEntity;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.data.repository.CredentialRepository;
import com.mipsas.poko.data.repository.UserRepository;
import com.mipsas.poko.security.jwt.JwtUser;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(NOT_EXISTS_USER::getException);
    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        UserEntity user = getUserById(request.id());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity user = getUserById(id);
        user.setStatus(DELETED);
        userRepository.save(user);
    }

    @Override
    public List<UserLocationEntity> getUserLocations(Long id) {
        return List.copyOf(getUserById(id).getUserLocations());
    }

    @Override
    public List<MetaDataEntity> getUserMetaData(Long id) {
        return List.copyOf(getUserById(id).getUserMetaData());
    }

    @Override
    public void registerUser(UserRegisterRequest request) {
        userRepository.findByNickName(request.nickName())
                .ifPresent(user -> EXISTS_USER.throwException());

        credentialRepository.findByEmail(request.email())
                .ifPresent(c -> EXISTS_CREDENTIAL.throwException());

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
    public UserEntity getAuthenticatedUser() {
        return userRepository.findByNickName(getAuthenticatedUserName())
                .orElse(null);
    }

    private String getAuthenticatedUserName() {
        return Optional.ofNullable(getAuthentication())
                .map(Principal::getName)
                .orElse(null);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
