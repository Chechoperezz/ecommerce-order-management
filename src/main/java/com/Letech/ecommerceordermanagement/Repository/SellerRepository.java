package com.Letech.ecommerceordermanagement.Repository;

import com.Letech.ecommerceordermanagement.Entity.Seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsBySellerGmail(String sellerGmail);

    boolean existsBySellerPhone(String sellerPhone);

    boolean existsBySellerUserName(String sellerUserName);
}
