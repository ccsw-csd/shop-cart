package com.cap.stock.stock.producer;

import com.cap.stock.stock.request.ShopOrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  ObjectMapper mapper = new ObjectMapper();

  public void sendMessage(String topic, ShopOrderRequest request) {

    try {
      String message = mapper.writeValueAsString(request);

      ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

      future.addCallback(new ListenableFutureCallback<>() {
        @Override
        public void onSuccess(SendResult<String, String> result) {
          System.out.println("Message has been sent: " + message);
        }

        @Override
        public void onFailure(Throwable ex) {
          System.out.println("Something went wrong with the message: " +  message);
        }
      });

    } catch (JsonProcessingException e) {
      System.out.println("Error parsing request");
    }
  }
}