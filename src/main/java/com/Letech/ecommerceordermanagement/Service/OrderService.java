package com.Letech.ecommerceordermanagement.Service;

import com.Letech.ecommerceordermanagement.Entity.*;
import com.Letech.ecommerceordermanagement.Entity.DTOS.CarItemDTO;
import com.Letech.ecommerceordermanagement.Entity.DTOS.ShoppingCartDTO;
import com.Letech.ecommerceordermanagement.Entity.Items.OrderItem;
import com.Letech.ecommerceordermanagement.Repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    /*
    MODIFICAR: EN LUGAR DE OBTENER ID'S, USAR OBJETOS
                IMPLEMENTAR STRIPE PARA MODIFICAR EL STATUS DE LSA ORDENES
                
     */


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.order-events}")
    private String kafkaOrderEventsTopic;

//    @Value("${stripe.secret-key}")
//    private String stripeSecretKey;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(ShoppingCartDTO shoppingCartDTO) {
        Order order = new Order();
        order.setAccountId(shoppingCartDTO.getAccountId());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CarItemDTO cartItemDTO : shoppingCartDTO.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItemDTO.getProduct());
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(calculateTotalPrice(orderItems));
        return orderRepository.save(order);

    }

    private Double calculateTotalPrice(List<OrderItem> orderItems) {
        double totalPrice = 0.0;
//        for (OrderItem item : orderItems) {
//            totalPrice += item.getProduct().getProductPrice() * item.getQuantity();
//        }
        return totalPrice;
    }

//   @KafkaListener(topics = "shopping-cart-checkout-topic", groupId = "order-group")
    public Order handleShoppingCartCheckout(ShoppingCartDTO shoppingCartDTO) {
        return createOrder(shoppingCartDTO);
    }

    private boolean processPayment(Order order) {
        try {
//            Stripe.apiKey = stripeSecretKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) (order.getTotalPrice() * 100));
            params.put("currency", "usd");
            params.put("source", "tok_visa");

            Charge charge = Charge.create(params);

            return charge.getPaid();
        } catch (StripeException e) {

            e.printStackTrace();
            return false;
        }
    }


}



