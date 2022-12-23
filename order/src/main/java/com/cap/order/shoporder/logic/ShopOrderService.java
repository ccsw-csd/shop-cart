package com.cap.order.shoporder.logic;

import com.cap.order.shoporder.producer.KafkaProducer;
import com.cap.order.shoporder.request.ShopOrderDataRequest;
import com.cap.order.shoporder.request.ShopOrderRequest;
import com.cap.order.shoporder.model.ShopOrderEntity;
import com.cap.order.shoporder.model.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopOrderService {

    @Autowired
    private ShopOrderRepository orderRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void purchase(ShopOrderDataRequest data) {

        data.setUuid(UUID.randomUUID().toString());

        create(data);

        ShopOrderRequest request = new ShopOrderRequest();
        request.setSuccess(true);
        request.setData(data);

        kafkaProducer.sendMessage("stock", request);
    }

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            finalize(request);
        } else {
            cancel(request);
        }
    }

    public void finalize(ShopOrderRequest request) {

        update(request.getData());

        kafkaProducer.sendMessage("notification", request);
    }

    public void cancel(ShopOrderRequest request) {

        delete(request.getData());

        kafkaProducer.sendMessage("notification", request);
    }

    public void create(ShopOrderDataRequest data) {

        ShopOrderEntity order = new ShopOrderEntity();

        order.setUuid(data.getUuid());
        order.setCustomer(data.getCustomer());
        order.setEmail(data.getEmail());
        order.setAddress(data.getAddress());
        order.setCredit(data.getCredit());
        order.setProduct(data.getProduct());
        order.setQuantity(data.getQuantity());

        orderRepository.save(order);
    }

    public void update(ShopOrderDataRequest data) {

        ShopOrderEntity order = orderRepository.getByUuid(data.getUuid());

        order.setPrice(data.getPrice());
        order.setPaid(data.getPaid());
        order.setShipment(data.getShipment());
        order.setInvoice(data.getInvoice());

        orderRepository.save(order);
    }

    public void delete(ShopOrderDataRequest data){

        ShopOrderEntity order = orderRepository.getByUuid(data.getUuid());

        orderRepository.delete(order);
    }
}
