package br.devdeloop.uepb.dtos.register;

import br.devdeloop.uepb.util.AppUserRoleEnum;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(String username, String password, AppUserRoleEnum role) {
}
