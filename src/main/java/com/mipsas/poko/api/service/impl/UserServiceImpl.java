package com.mipsas.poko.api.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.NOT_EXISTS_USER;
import com.mipsas.poko.api.model.request.UpdateUserRequest;
import com.mipsas.poko.api.service.UserService;
import com.mipsas.poko.data.entity.MetaDataEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.data.repository.UserRepository;
import com.mipsas.poko.security.jwt.JwtUser;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
    public List<UserLocationEntity> getUserLocations(Long id) {
        return List.copyOf(getUserById(id).getUserLocations());
    }

    @Override
    public List<MetaDataEntity> getUserMetaData(Long id) {
        return List.copyOf(getUserById(id).getUserMetaData());
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
