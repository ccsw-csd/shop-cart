package com.cap.stock.stock.logic;

import com.cap.stock.stock.producer.KafkaProducer;
import com.cap.stock.stock.request.ShopOrderRequest;
import com.cap.stock.stock.model.StockEntity;
import com.cap.stock.stock.model.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public void process(ShopOrderRequest request){
        if(request.getSuccess()){
            reserve(request);
        } else {
            cancel(request);
        }
    }

    public void reserve(ShopOrderRequest request) {

        StockEntity entity = stockRepository.getByProduct(request.getData().getProduct());

        if(entity != null && entity.getStock() >= request.getData().getQuantity()){

            entity.setStock(entity.getStock() - request.getData().getQuantity());
            stockRepository.save(entity);

            request.getData().setPrice(entity.getPrice());
            kafkaProducer.sendMessage("payment", request);
        } else {
            request.setSuccess(false);
            kafkaProducer.sendMessage("shop_order", request);
        }

    }

    public void cancel(ShopOrderRequest request) {

        StockEntity entity = stockRepository.getByProduct(request.getData().getProduct());

        entity.setStock(entity.getStock() + request.getData().getQuantity());
        stockRepository.save(entity);

        kafkaProducer.sendMessage("shop_order", request);
    }
}
