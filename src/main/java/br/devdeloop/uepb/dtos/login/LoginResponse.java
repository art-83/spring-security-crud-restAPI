package br.devdeloop.uepb.dtos.login;

public record LoginResponse(String acessToken, Long expiresIn) {
}
