package com.Letech.ecommerceordermanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String customerUserName;
    private String customerFirstName;
    private String customerLastName;
    private LocalDate customerBirthday;

    private String customerGmail;
    private String customerPhone;
    private String customerPassword;

    @OneToOne
    private ShoppingCart shoppingCart;
}




