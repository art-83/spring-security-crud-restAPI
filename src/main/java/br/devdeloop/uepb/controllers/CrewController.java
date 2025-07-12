package br.devdeloop.uepb.controllers;

import br.devdeloop.uepb.dtos.container.ShipContainerRequest;
import br.devdeloop.uepb.services.container.ShipContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    private ShipContainerService shipContainerService;

    @PostMapping("/add-container")
    public ResponseEntity<?> addContainer(@RequestBody ShipContainerRequest shipContainerRequest) {
        return shipContainerService.addUpdateShipContainer(shipContainerRequest);
    }
}
