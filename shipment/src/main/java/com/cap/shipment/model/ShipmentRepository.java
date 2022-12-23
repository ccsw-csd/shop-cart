package com.cap.shipment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {

    ShipmentEntity getByUuid(String uuid);

}
