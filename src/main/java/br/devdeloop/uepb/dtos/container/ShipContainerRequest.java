package br.devdeloop.uepb.dtos.container;

import br.devdeloop.uepb.models.AppUser;
import br.devdeloop.uepb.util.PusherEnum;
import br.devdeloop.uepb.util.StatusEnum;

public record ShipContainerRequest(
        String id,
        PusherEnum pusher,
        Integer shipQuantity,
        String branchExitDateTime,
        String destinationArrivalDateTime,
        String destinationExitDateTime,
        String branchArrivalDateTime,
        String observation,
        StatusEnum status
) {
}