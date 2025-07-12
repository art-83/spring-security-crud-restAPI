package br.devdeloop.uepb.controllers;

import br.devdeloop.uepb.dtos.login.LoginRequest;
import br.devdeloop.uepb.dtos.login.LoginResponse;
import br.devdeloop.uepb.services.user.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) throws AccessDeniedException {
        return loginService.login(loginRequest);
    }
}
