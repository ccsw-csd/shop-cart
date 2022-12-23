package com.cap.notification.logic;

import com.cap.notification.request.ShopOrderRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            confirmed(request);
        } else {
            canceled(request);
        }
    }

    public void confirmed(ShopOrderRequest request) {

        System.out.println("Email to: " + request.getData().getEmail() + " | Order: " + request.getData().getInvoice() + " its confirmed and will be received soon");
    }

    public void canceled(ShopOrderRequest request) {

        System.out.println("Email to: " + request.getData().getEmail() + " | Order: " + request.getData().getUuid() + " its canceled");
    }
}
