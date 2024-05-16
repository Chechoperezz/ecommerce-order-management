package com.Letech.ecommerceordermanagement.Controller;

import com.Letech.ecommerceordermanagement.Entity.DTOS.ShoppingCartDTO;
import com.Letech.ecommerceordermanagement.Entity.ShoppingCart;
import com.Letech.ecommerceordermanagement.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private OrderService orderService;


    @KafkaListener(topics = "shopping-cart-checkout-topic", groupId = "order-processing-group")
    public ResponseEntity<String> placeOrder(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        orderService.handleShoppingCartCheckout(shoppingCartDTO);
        return ResponseEntity.ok("Order placed successfully. Payment processing in progress.");
    }
}
