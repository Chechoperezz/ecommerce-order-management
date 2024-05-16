package com.Letech.ecommerceordermanagement.Service;

import com.Letech.ecommerceordermanagement.Entity.Customer;
import com.Letech.ecommerceordermanagement.Exceptions.*;
import com.Letech.ecommerceordermanagement.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<Object> saveCustomer(Customer customer) {
        try {
            checkExistingGmail(customer.getCustomerGmail());
            checkExistingPhone(customer.getCustomerPhone());
            checkExistingUserName(customer.getCustomerUserName());
            checkCustomerAdult(customer.getCustomerBirthday());

            customer.setCustomerPassword(passwordEncoder.encode(customer.getCustomerPassword()));
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (DuplicateEmailException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate email: " + e.getMessage(), "email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (DuplicatePhoneException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate phone number: " + e.getMessage(), "phone");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (DuplicateUsernameException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate username: " + e.getMessage(), "username");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (CustomerNotAdultException e) {
            ErrorResponse errorResponse = new ErrorResponse("User not Adult: " + e.getMessage(), "username");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ResponseEntity<Object> updateCustomer(Long accountId, Customer updateCustomer) {

        Customer existingCustomer = customerRepository.findById(accountId).
                orElseThrow(()-> new CustomerNotFoundException("User Not found"));

        try {

            checkExistingGmail(updateCustomer.getCustomerGmail());
            checkExistingPhone(updateCustomer.getCustomerPhone());
            checkExistingUserName(updateCustomer.getCustomerUserName());


            existingCustomer.setCustomerFirstName(updateCustomer.getCustomerFirstName());
            existingCustomer.setCustomerLastName(updateCustomer.getCustomerLastName());
            existingCustomer.setCustomerPhone(updateCustomer.getCustomerPhone());
            existingCustomer.setCustomerGmail(updateCustomer.getCustomerGmail());
            existingCustomer.setCustomerUserName(updateCustomer.getCustomerUserName());


            return ResponseEntity.ok(existingCustomer);

        } catch (DuplicateEmailException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate email: " + e.getMessage(), "email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (DuplicatePhoneException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate phone number: " + e.getMessage(), "phone");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (DuplicateUsernameException e) {
            ErrorResponse errorResponse = new ErrorResponse("Duplicate username: " + e.getMessage(), "username");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    public ResponseEntity<Object> updateCustomerPassword(Long accountId, String newPassword) {
        try {
            Customer existingCustomer = customerRepository.findById(accountId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

            String hashedPassword = passwordEncoder.encode(newPassword);


            existingCustomer.setCustomerPassword(hashedPassword);


            Customer savedCustomer = customerRepository.save(existingCustomer);


            return ResponseEntity.ok(savedCustomer);

        } catch (CustomerNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Customer not found: " + e.getMessage(), "AccountId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    private void checkExistingGmail(String customerEmail) {
        boolean exists = customerRepository.findCustomerByCustomerGmail(customerEmail);
        if (exists) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    private void checkExistingPhone(String customerPhone) {
        boolean exists = customerRepository.existsByCustomerPhone(customerPhone);
        if (exists) {
            throw  new DuplicatePhoneException("Phone already exists");
        }
    }

    private void checkExistingUserName(String customerUserName) {
        boolean exists = customerRepository.existsByCustomerUserName(customerUserName);
        if (exists) {
            throw new DuplicateUsernameException("Username already in use");
        }
    }

    private void checkCustomerAdult(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();


        Period period = Period.between(birthday, currentDate);
        int age = period.getYears();


        int adultAgeThreshold = 18;


        if (age < adultAgeThreshold) {
            throw new CustomerNotAdultException("Customer is not an adult");
        }
    }




}
