package com.Letech.ecommerceordermanagement.Controller;

import com.Letech.ecommerceordermanagement.Entity.Customer;
import com.Letech.ecommerceordermanagement.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
        return ResponseEntity.ok("Registration Succesful");

    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable("id") Long customerId,@RequestBody Customer customer){
        customerService.updateCustomer(customerId, customer);
        return ResponseEntity.ok("Update Successful");
    }

    @PutMapping("/update/id/{id}/password/{password}")
    public ResponseEntity<String> updatePassword(
            @PathVariable("id") Long customerId, @PathVariable("password") String newPassword){
        customerService.updateCustomerPassword(customerId,newPassword);
        return ResponseEntity.ok("Password Changed Successfully");

    }
    

}
