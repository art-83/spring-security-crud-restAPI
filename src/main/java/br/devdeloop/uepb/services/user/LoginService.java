package br.devdeloop.uepb.services.user;

import br.devdeloop.uepb.dtos.login.LoginRequest;
import br.devdeloop.uepb.dtos.login.LoginResponse;
import br.devdeloop.uepb.models.AppUser;
import br.devdeloop.uepb.repositories.AppUserRepository;
import br.devdeloop.uepb.security.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AppUserRepository appUserRepository;

    public LoginResponse login(LoginRequest loginRequest) throws AccessDeniedException {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            authenticationManager.authenticate(auth);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Bad credentials.");
        }

        AppUser appUser = appUserRepository.findByUsername(loginRequest.username());

        if (!appUser.getRole().equals(loginRequest.role())) {
            throw new AccessDeniedException("User role mismatch, expected: " + appUser.getRole()
                    + ", received: " + loginRequest.role() + ".");
        }

        return tokenService.generateToken(loginRequest);
    }
}
