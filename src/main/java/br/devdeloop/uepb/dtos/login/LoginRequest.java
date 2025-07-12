package br.devdeloop.uepb.dtos.login;

import br.devdeloop.uepb.util.AppUserRoleEnum;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username, @NotBlank String password, @NotBlank AppUserRoleEnum role) {
}
