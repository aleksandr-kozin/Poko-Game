package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.AUTH;
import static com.mipsas.poko.api.Paths.REGISTRATION;
import static com.mipsas.poko.api.Paths.SIGN_IN;
import static com.mipsas.poko.api.Paths.SIGN_OUT;
import com.mipsas.poko.api.model.request.SignInRequest;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.model.response.SignInResponse;
import com.mipsas.poko.api.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PermitAll
    @Operation(summary = "Register new user")
    @PostMapping(REGISTRATION)
    public void registration(@RequestBody @Valid UserRegisterRequest request) {
        authenticationService.registerUser(request);
    }

    @PermitAll
    @Operation(summary = "User sign in")
    @PostMapping(SIGN_IN)
    public SignInResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PermitAll
    @Operation(summary = "User sign out")
    @PostMapping(SIGN_OUT)
    public void signOut(HttpServletRequest request) {
        authenticationService.signOut(request);
    }
}
