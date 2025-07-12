package br.devdeloop.uepb.dtos.container;

import br.devdeloop.uepb.util.PusherEnum;
import br.devdeloop.uepb.util.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShipContainerRequest(
        @NotBlank String id,
        @NotNull PusherEnum pusher,
        @NotNull Integer shipQuantity,
        @NotBlank String branchExitDateTime,
        String destinationArrivalDateTime,
        String destinationExitDateTime,
        String branchArrivalDateTime,
        String observation,
        StatusEnum status
) {
}
