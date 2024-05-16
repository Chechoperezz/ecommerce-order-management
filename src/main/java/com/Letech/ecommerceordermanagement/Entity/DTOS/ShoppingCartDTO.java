package com.Letech.ecommerceordermanagement.Entity.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private Long accountId;
    private List<CarItemDTO> cartItems;


}

