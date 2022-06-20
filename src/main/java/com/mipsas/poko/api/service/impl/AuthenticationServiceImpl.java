package com.mipsas.poko.api.service.impl;

import static com.mipsas.poko.api.exception.ErrorStatus.*;
import com.mipsas.poko.api.model.request.SignInRequest;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.model.response.SignInResponse;
import com.mipsas.poko.api.service.AuthenticationService;
import static com.mipsas.poko.common.enums.UserAuthority.USER;
import static com.mipsas.poko.common.enums.UserStatus.ACTIVE;
import static com.mipsas.poko.common.enums.UserStatus.NOT_ACTIVE;
import com.mipsas.poko.data.entity.CredentialEntity;
import com.mipsas.poko.data.entity.JwtBlackListEntity;
import com.mipsas.poko.data.entity.UserEntity;
import com.mipsas.poko.data.repository.CredentialRepository;
import com.mipsas.poko.data.repository.UserRepository;
import com.mipsas.poko.security.jwt.JwtUser;
import com.mipsas.poko.security.jwt.UserAndClaims;
import com.mipsas.poko.security.service.JwtBlackListService;
import com.mipsas.poko.security.service.JwtService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtBlackListService jwtBlackListService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignInResponse signIn(SignInRequest request) {
        return Optional.ofNullable(authenticate(request))
                .map(this::activateUser)
                .map(jwtService::createToken)
                .map(SignInResponse::new)
                .orElseThrow(BAD_CREDENTIALS::getException);
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
    public void signOut(HttpServletRequest request) {
        String token = jwtService.resolveToken(request);
        UserAndClaims userNameWithClaims = jwtService.getUserNameWithClaims(token);

        userRepository.findByNickName(userNameWithClaims.getUserName())
                .ifPresentOrElse(user -> {
                    user.setStatus(NOT_ACTIVE);
                    userRepository.save(user);
                }, NOT_EXISTS_USER::throwException);

        jwtBlackListService.addToBlackList(JwtBlackListEntity.builder()
                .token(token)
                .expirationDate(jwtService.getExpirationDate(token))
                .build());
    }

    private Authentication authenticate(SignInRequest request) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (DisabledException e) {
            throw USER_DISABLED.getException();
        } catch (LockedException e) {
            throw USER_LOCKED.getException();
        } catch (BadCredentialsException e) {
            throw BAD_CREDENTIALS.getException();
        }
    }

    private JwtUser activateUser(Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();

        userRepository.findByNickName(jwtUser.getNickName())
                .ifPresent(user -> {
                    user.setStatus(ACTIVE);
                    userRepository.save(user);
                });

        return jwtUser;
    }
}
