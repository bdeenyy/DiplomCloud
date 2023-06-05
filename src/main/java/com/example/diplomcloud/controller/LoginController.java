package com.example.diplomcloud.controller;

import com.example.diplomcloud.dto.LoginRequest;
import com.example.diplomcloud.dto.LoginResponse;
import com.example.diplomcloud.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String authToken = loginService.login(request.getLogin(), request.getPassword());
        return new LoginResponse(authToken);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String authToken) {
        loginService.logout(authToken);
    }
}
