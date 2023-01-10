package com.cap.notification.logic;

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

    public void confirmed(ShopOrderRequest request) {

        sendMessage(request.getData().getEmail(), "Confirmation", "Order: " + request.getData().getInvoice() + " its confirmed and will be received soon");
    }

    public void cancelled(ShopOrderRequest request) {

        sendMessage(request.getData().getEmail(), "Cancellation", "Order: " + request.getData().getUuid() + " its cancelled");
    }

    public void sendMessage(String to, String subject, String text) {

        System.out.println("Email to: " + to + " | Status: " + subject + " | " + text);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
