package com.cap.order.shoporder.controller;

import com.cap.order.shoporder.request.ShopOrderDataRequest;
import com.cap.order.shoporder.request.ShopOrderRequest;
import com.cap.order.shoporder.logic.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @PostMapping("/purchase")
    public ResponseEntity<HttpStatus> purchase(@RequestBody ShopOrderDataRequest request) {

        shopOrderService.purchase(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
