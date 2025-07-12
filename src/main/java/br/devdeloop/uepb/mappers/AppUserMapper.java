package br.devdeloop.uepb.mappers;

import br.devdeloop.uepb.dtos.register.RegisterRequest;
import br.devdeloop.uepb.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser registerRequestToEntity(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequest.username());

        appUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        appUser.setRole(registerRequest.role());

        return appUser;
    }
}
