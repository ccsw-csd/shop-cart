package com.cap.order.shoporder.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrderEntity, Long> {

    ShopOrderEntity getByUuid(String uuid);

}
