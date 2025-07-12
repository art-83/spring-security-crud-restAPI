package br.devdeloop.uepb.dtos.register;

import br.devdeloop.uepb.util.AppUserRoleEnum;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank String username, @NotBlank String password, AppUserRoleEnum role) {
}
