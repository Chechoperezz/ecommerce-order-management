package com.Letech.ecommerceordermanagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
    private String userId;
    private List<Product> products;
    private OrderEventType eventType;

    public enum OrderEventType {
        PLACE_ORDER,

    }


}

