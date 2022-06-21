package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.*;
import com.mipsas.poko.api.model.request.ResetPasswordRequest;
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
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Reset user password")
    @PostMapping(RESET_PASSWORD)
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
    }
}
