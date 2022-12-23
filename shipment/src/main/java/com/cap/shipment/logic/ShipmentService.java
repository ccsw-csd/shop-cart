package com.cap.shipment.logic;

import com.cap.shipment.model.ShipmentEntity;
import com.cap.shipment.model.ShipmentRepository;
import com.cap.shipment.producer.KafkaProducer;
import com.cap.shipment.request.ShopOrderDataRequest;
import com.cap.shipment.request.ShopOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            ship(request);
        } else {
            cancel(request);
        }
    }

    public void ship(ShopOrderRequest request) {

        if(request.getData().getAddress() != null){

            request.getData().setShipment(LocalDateTime.now().toString());
            save(request.getData());

            kafkaProducer.sendMessage("invoice", request);
        } else {
            request.setSuccess(false);
            kafkaProducer.sendMessage("payment", request);
        }

    }

    public void cancel(ShopOrderRequest request) {

        ShipmentEntity entity = shipmentRepository.getByUuid(request.getData().getUuid());
        shipmentRepository.delete(entity);

        kafkaProducer.sendMessage("payment", request);
    }

    public void save(ShopOrderDataRequest data) {

        ShipmentEntity shipment = new ShipmentEntity();

        shipment.setUuid(data.getUuid());
        shipment.setCustomer(data.getCustomer());
        shipment.setAddress(data.getAddress());
        shipment.setShipment(data.getShipment());

        shipmentRepository.save(shipment);
    }
}
