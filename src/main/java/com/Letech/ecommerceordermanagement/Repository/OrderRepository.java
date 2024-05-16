package com.Letech.ecommerceordermanagement.Repository;

import com.Letech.ecommerceordermanagement.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

}
