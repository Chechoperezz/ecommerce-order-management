package com.Letech.ecommerceordermanagement.Entity.DTOS;

import com.Letech.ecommerceordermanagement.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarItemDTO {
    private Product product;
    private Integer quantity;
}
