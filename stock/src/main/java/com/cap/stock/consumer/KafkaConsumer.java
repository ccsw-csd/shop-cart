package com.cap.stock.consumer;

import com.cap.stock.logic.StockService;
import com.cap.stock.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Autowired
  private StockService stockService;

  ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "stock")
  public void listener(String message) {

    try {
      System.out.println("Message has been received: " + message);
      ShopOrderRequest request = mapper.readValue(message, ShopOrderRequest.class);

      if(groupId.equals(request.getGroupId())) {
        stockService.process(request);
      }

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }

}