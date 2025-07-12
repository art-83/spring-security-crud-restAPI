package br.devdeloop.uepb.services.container;

import br.devdeloop.uepb.dtos.container.ShipContainerRequest;
import br.devdeloop.uepb.mappers.ShipContainerMapper;
import br.devdeloop.uepb.models.ShipContainer;
import br.devdeloop.uepb.repositories.ShipContainerRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/*
    JSON EXPECTED
    {
        "id": ,
        "pusher": ,
        "shipQuantity": , (number)
        "branchExitDateTime": ,
        "destinationArrivalDateTime": ,
        "destinationExitDateTime": ,
        "branchArrivalDateTime":,
        "observation":,
        "status":
    }
*/

@Service
public class ShipContainerService {

    @Autowired
    private ShipContainerRepository shipContainerRepository;

    @Autowired
    private ShipContainerMapper shipContainerMapper;

    @Transactional
    public ResponseEntity<?> saveShipContainer(ShipContainerRequest shipContainerRequest) {
        try {
            shipContainerRepository.save(shipContainerMapper.shipContainerRequestToEntity(shipContainerRequest));
        } catch (Exception e) {
            throw new PersistenceException("Fail to persist (On 'saveShipContainer').");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("New container added.");
    }

    @Transactional
    public ResponseEntity<?> updateShipContainer(ShipContainerRequest shipContainerRequest) {
        try {
            shipContainerRepository.save(shipContainerMapper.shipContainerRequestToEntity(shipContainerRequest));
        } catch (Exception e) {
            throw new PersistenceException("Fail to persist. (On 'updateShipContainer').");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Container updated.");
    }

    @Transactional
    public ResponseEntity<?> addUpdateShipContainer(ShipContainerRequest shipContainerRequest) {
        if (!shipContainerRepository.existsById(shipContainerRequest.id())) {
            return saveShipContainer(shipContainerRequest);
        }
        return updateShipContainer(shipContainerRequest);
    }

    public ShipContainer getShipContainerById(String id) {
        return shipContainerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ship container ID not found in database."));
    }

    public List<ShipContainer> getShipContainerAllData() {
        return shipContainerRepository.findAll();
    }
}