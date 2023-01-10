package com.cap.notification.logic;

import com.cap.notification.request.ShopOrderDataRequest;
import com.cap.notification.request.ShopOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            confirmed(request);
        } else {
            cancelled(request);
        }
    }

    private void confirmed(ShopOrderRequest request) {

        sendMessage(request.getData().getEmail(), "Confirmation: " + request.getGroupId(), "Order: " + request.getData().getInvoice() + " its confirmed and will be received soon");
    }

    private void cancelled(ShopOrderRequest request) {

        sendMessage(request.getData().getEmail(), "Cancellation: " + request.getGroupId(), "Order: " + request.getData().getUuid() + " its cancelled because " + getError(request.getData()));
    }

    private void sendMessage(String to, String subject, String text) {

        System.out.println("Email to: " + to + " | Status: " + subject + " | " + text);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    private String getError(ShopOrderDataRequest request){

        if(request.getPrice() == null){
            return "product out of stock";
        } else if (request.getPaid() == null) {
            return "credit card not provided";
        } else if (request.getShipment() == null) {
            return "address not provided";
        } else if (request.getInvoice() == null) {
            return "error during invoice generation";
        } else {
            return "unhandled error";
        }
    }
}
