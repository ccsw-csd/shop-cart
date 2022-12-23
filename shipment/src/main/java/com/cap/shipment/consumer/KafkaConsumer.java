package com.cap.shipment.consumer;

import com.cap.shipment.logic.ShipmentService;
import com.cap.shipment.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @Autowired
  private ShipmentService shipmentService;

  ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "shipment")
  public void listener(String message) {

    try {
      System.out.println("Message has been received: " + message);
      ShopOrderRequest request = mapper.readValue(message, ShopOrderRequest.class);
      shipmentService.process(request);

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }

}