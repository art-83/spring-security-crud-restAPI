package br.devdeloop.uepb.services.user;

import br.devdeloop.uepb.dtos.register.RegisterRequest;
import br.devdeloop.uepb.mappers.AppUserMapper;
import br.devdeloop.uepb.models.AppUser;
import br.devdeloop.uepb.repositories.AppUserRepository;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> registerCredentials(@Valid RegisterRequest registerRequest) throws BadRequestException {
        AppUser appUser = appUserRepository.findByUsername(registerRequest.username());
        if (appUser != null) {
            throw new BadRequestException("Username already in database.");
        }
        appUserRepository.save(appUserMapper.registerRequestToEntity(registerRequest));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User registered.");
    }

    @Transactional
    public ResponseEntity<?> updateCrentials(@Valid RegisterRequest registerRequest) {
        appUserRepository.updateByUsername(
                registerRequest.username(),
                registerRequest.role(),
                passwordEncoder.encode(registerRequest.password()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User updated.");
    }
}
