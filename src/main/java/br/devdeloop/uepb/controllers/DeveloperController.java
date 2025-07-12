package br.devdeloop.uepb.controllers;

import br.devdeloop.uepb.dtos.register.RegisterRequest;
import br.devdeloop.uepb.services.user.UserManagementService;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DeveloperController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) throws BadRequestException {
        return userManagementService.registerCredentials(registerRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid RegisterRequest registerRequest) {
        return userManagementService.updateCrentials(registerRequest);
    }
}