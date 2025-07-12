package br.devdeloop.uepb.models;

import br.devdeloop.uepb.util.PusherEnum;
import br.devdeloop.uepb.util.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ship_container")
public class ShipContainer {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "pusher")
    @Enumerated(EnumType.STRING)
    private PusherEnum pusher;

    @Column(name = "ship_quantity")
    private Integer shipQuantity;

    @Column(name = "branch_exit_date_time")
    private String branchExitDateTime;

    @Column(name = "destination_arrival_date_time")
    private String destinationArrivalDateTime;

    @Column(name = "destination_exit_date_time")
    private String destinationExitDateTime;

    @Column(name = "branch_arrival_date_time")
    private String branchArrivalDateTime;

    @Column(name = "observations")
    private String observation;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
