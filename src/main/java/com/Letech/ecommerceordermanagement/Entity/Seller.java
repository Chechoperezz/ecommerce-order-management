package com.Letech.ecommerceordermanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long sellerId;
    private String sellerSurname;
    private String sellerLastName;
    private String sellerUserName;
    private String sellerGmail;
    private String sellerPhone;
    private String sellerNIF;
    private LocalDate sellerBirthday;
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> sellerProducts = new ArrayList<>();


}
