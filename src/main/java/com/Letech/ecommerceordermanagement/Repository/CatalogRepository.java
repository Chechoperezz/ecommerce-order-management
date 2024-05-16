package com.Letech.ecommerceordermanagement.Repository;

import com.Letech.ecommerceordermanagement.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CatalogRepository extends JpaRepository<Product,Long> {


}
