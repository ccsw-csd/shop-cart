package com.cap.notification.consumer;

import com.cap.notification.logic.NotificationService;
import com.cap.notification.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  @Autowired
  private NotificationService notificationService;

  ObjectMapper mapper = new ObjectMapper();

  @KafkaListener(topics = "notification")
  public void listener(String message) {

    try {
      System.out.println("Message has been received: " + message);
      ShopOrderRequest request = mapper.readValue(message, ShopOrderRequest.class);
      notificationService.process(request);

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }

}