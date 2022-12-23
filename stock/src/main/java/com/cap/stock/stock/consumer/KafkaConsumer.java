package com.cap.stock.stock.consumer;

import com.cap.stock.stock.request.ShopOrderRequest;
import com.cap.stock.stock.logic.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @Autowired
  private StockService stockService;

  ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "stock")
  public void listener(String message) {

    try {
      System.out.println("Message has been received: " + message);
      ShopOrderRequest request = mapper.readValue(message, ShopOrderRequest.class);
      stockService.process(request);

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }

}