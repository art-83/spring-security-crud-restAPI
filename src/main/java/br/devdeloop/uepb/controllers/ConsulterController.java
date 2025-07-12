package br.devdeloop.uepb.controllers;

import br.devdeloop.uepb.models.ShipContainer;
import br.devdeloop.uepb.services.container.ShipContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consult")
public class ConsulterController {

    @Autowired
    private ShipContainerService shipContainerService;

    @GetMapping("/id/{id}")
    public ShipContainer getShipContainerDataById(@PathVariable String id) {
        return shipContainerService.getShipContainerById(id);
    }

    @GetMapping("/all-data")
    public List<ShipContainer> getShipContainerAllData() {
        return shipContainerService.getShipContainerAllData();
    }
}