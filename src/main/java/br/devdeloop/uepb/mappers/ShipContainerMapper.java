package br.devdeloop.uepb.mappers;

import br.devdeloop.uepb.dtos.container.ShipContainerRequest;
import br.devdeloop.uepb.models.AppUser;
import br.devdeloop.uepb.models.ShipContainer;
import br.devdeloop.uepb.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ShipContainerMapper {

    @Autowired
    private AppUserRepository appUserRepository;

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

        String appUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        shipContainer.setAppUser(appUserRepository.findByUsername(appUserUsername));
        return shipContainer;
    }
}
