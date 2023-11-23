package com.cap.shipment.producer;

import com.cap.shipment.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  ObjectMapper mapper = new ObjectMapper();

  public void sendMessage(String topic, ShopOrderRequest request) {

    try {
      String message = mapper.writeValueAsString(request);

      CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

      future.whenComplete((result, ex) -> {
        if (ex == null) {
          System.out.println("Message has been sent: " + message);
        } else {
          System.out.println("Something went wrong with the message: " +  message);
        }
      });

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }
}