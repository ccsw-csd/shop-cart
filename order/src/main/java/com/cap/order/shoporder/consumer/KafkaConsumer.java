package com.cap.order.shoporder.consumer;

import com.cap.order.shoporder.logic.ShopOrderService;
import com.cap.order.shoporder.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @Autowired
  private ShopOrderService shopOrderService;

  ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "shop_order")
  public void listener(String message) {

    try {
      System.out.println("Message has been received: " + message);
      ShopOrderRequest request = mapper.readValue(message, ShopOrderRequest.class);
      shopOrderService.process(request);

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }

}