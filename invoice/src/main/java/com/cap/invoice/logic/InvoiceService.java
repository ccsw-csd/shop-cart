package com.cap.invoice.logic;

import com.cap.invoice.producer.KafkaProducer;
import com.cap.invoice.request.ShopOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Autowired
    private KafkaProducer kafkaProducer;

    public void invoice(ShopOrderRequest request) {

        String invoice = request.getData().getUuid() + "-" + request.getData().getCustomer() + "-" + request.getData().getProduct();

        if(invoice.length() < 60){

            request.getData().setInvoice(invoice);

            kafkaProducer.sendMessage("shop_order", request);
        } else {
            request.setSuccess(false);
            kafkaProducer.sendMessage("shipment", request);
        }

    }
}
