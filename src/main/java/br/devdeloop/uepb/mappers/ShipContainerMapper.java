package br.devdeloop.uepb.mappers;

import br.devdeloop.uepb.dtos.container.ShipContainerRequest;
import br.devdeloop.uepb.models.ShipContainer;
import org.springframework.stereotype.Component;

@Component
public class ShipContainerMapper {

    public ShipContainer shipContainerRequestToEntity(ShipContainerRequest shipContainerRequest) {
        ShipContainer shipContainer = new ShipContainer();

        shipContainer.setId(shipContainerRequest.id());
        shipContainer.setPusher(shipContainerRequest.pusher());
        shipContainer.setShipQuantity(shipContainerRequest.shipQuantity());
        shipContainer.setBranchExitDateTime(shipContainerRequest.branchExitDateTime());
        shipContainer.setDestinationArrivalDateTime(shipContainerRequest.destinationArrivalDateTime());
        shipContainer.setDestinationExitDateTime(shipContainerRequest.destinationExitDateTime());
        shipContainer.setBranchArrivalDateTime(shipContainerRequest.branchArrivalDateTime());
        shipContainer.setObservation(shipContainerRequest.observation());
        shipContainer.setStatus(shipContainerRequest.status());

        return shipContainer;
    }
}
