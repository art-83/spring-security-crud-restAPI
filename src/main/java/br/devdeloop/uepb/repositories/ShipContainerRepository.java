package br.devdeloop.uepb.repositories;

import br.devdeloop.uepb.models.ShipContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipContainerRepository extends JpaRepository<ShipContainer, String> {
}
