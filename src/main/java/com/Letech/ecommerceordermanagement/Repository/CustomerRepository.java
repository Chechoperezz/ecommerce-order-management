package com.Letech.ecommerceordermanagement.Repository;

import com.Letech.ecommerceordermanagement.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    String deleteCustomerByAccountId(Long userId);

    Customer findCustomerByAccountId(Long userId);
    
    boolean findCustomerByCustomerGmail(String userGmail);

    boolean existsByCustomerPhone(String userPhone);

    boolean existsByCustomerUserName(String userName);

    boolean existsByCustomerPassword(String userPassword);

}
