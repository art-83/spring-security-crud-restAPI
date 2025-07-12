package br.devdeloop.uepb.security.services;

import br.devdeloop.uepb.dtos.login.LoginRequest;
import br.devdeloop.uepb.dtos.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public LoginResponse generateToken(LoginRequest loginRequest) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("reicon-api")
                .subject(loginRequest.username())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .claim("role", List.of(loginRequest.role().name()))
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(), claimsSet);

        return new LoginResponse(jwtEncoder.encode(parameters).getTokenValue(),
                Duration.between(Instant.now(), Instant.now().plusSeconds(3600)).getSeconds());
    }
}
