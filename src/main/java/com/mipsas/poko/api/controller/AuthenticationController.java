package com.mipsas.poko.api.controller;

import static com.mipsas.poko.api.Paths.AUTH;
import static com.mipsas.poko.api.Paths.REGISTRATION;
import static com.mipsas.poko.api.Paths.SIGN_OUT;
import com.mipsas.poko.api.model.request.UserRegisterRequest;
import com.mipsas.poko.api.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthenticationController {
    private final UserService userService;

    @PostMapping(REGISTRATION)
    public void registration(@RequestBody @Valid UserRegisterRequest request) {
        userService.registerUser(request);
    }

    @PostMapping(SIGN_OUT)
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        userService.signOut(request, response);
    }
}
