package com.cap.payment.logic;

import com.cap.payment.model.PaymentEntity;
import com.cap.payment.model.PaymentRepository;
import com.cap.payment.producer.KafkaProducer;
import com.cap.payment.request.ShopOrderDataRequest;
import com.cap.payment.request.ShopOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            pay(request);
        } else {
            cancel(request);
        }
    }

    public void pay(ShopOrderRequest request) {

        if(request.getData().getCredit() != null){

            request.getData().setPaid(request.getData().getPrice() * request.getData().getQuantity());
            save(request.getData());

            kafkaProducer.sendMessage("shipment", request);
        } else {
            request.setSuccess(false);
            kafkaProducer.sendMessage("stock", request);
        }

    }

    public void cancel(ShopOrderRequest request) {

        PaymentEntity entity = paymentRepository.getByUuid(request.getData().getUuid());
        paymentRepository.delete(entity);

        kafkaProducer.sendMessage("stock", request);
    }

    public void save(ShopOrderDataRequest data) {

        PaymentEntity payment = new PaymentEntity();

        payment.setUuid(data.getUuid());
        payment.setCustomer(data.getCustomer());
        payment.setCredit(data.getCredit());
        payment.setAmount(data.getPaid());

        paymentRepository.save(payment);
    }
}
