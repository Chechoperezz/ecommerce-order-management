package com.Letech.ecommerceordermanagement.Service;

import com.Letech.ecommerceordermanagement.Entity.Seller;
import com.Letech.ecommerceordermanagement.Exceptions.*;
import com.Letech.ecommerceordermanagement.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<Object> saveSeller(Seller seller) {
        try {
            checkExistingGmail(seller.getSellerGmail());
            checkExistingPhone(seller.getSellerPhone());
            checkExistingUserName(seller.getSellerUserName());
            checkSellerAdult(seller.getSellerBirthday());

            seller.setPassword(passwordEncoder.encode(seller.getPassword()));
            Seller savedSeller = sellerRepository.save(seller);
            return ResponseEntity.ok(savedSeller);
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

    public ResponseEntity<Object> updateSeller(Long sellerId, Seller updatedSeller) {

        Seller existingSeller = sellerRepository.findById(sellerId).
                orElseThrow(()-> new CustomerNotFoundException("User Not found"));

        try {

            checkExistingGmail(updatedSeller.getSellerGmail());
            checkExistingPhone(updatedSeller.getSellerPhone());
            checkExistingUserName(updatedSeller.getSellerUserName());


            existingSeller.setSellerSurname(updatedSeller.getSellerSurname());
            existingSeller.setSellerLastName(updatedSeller.getSellerLastName());
            existingSeller.setSellerPhone(updatedSeller.getSellerPhone());
            existingSeller.setSellerGmail(updatedSeller.getSellerGmail());
            existingSeller.setSellerUserName(updatedSeller.getSellerUserName());


            return ResponseEntity.ok(existingSeller);

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
    public ResponseEntity<Object> updatePassword(Long sellerId, String newPassword) {
        try {
            Seller existingSeller = sellerRepository.findById(sellerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Seller not found"));

            String hashedPassword = passwordEncoder.encode(newPassword);
            existingSeller.setPassword(hashedPassword);
            Seller savedSeller = sellerRepository.save(existingSeller);

            return ResponseEntity.ok(savedSeller);

        } catch (CustomerNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("Seller not found: " + e.getMessage(), "sellerId");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    private void checkExistingGmail(String sellerGmail) {
        boolean exists = sellerRepository.existsBySellerGmail(sellerGmail);
        if (exists) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    private void checkExistingPhone(String sellerPhone) {
        boolean exists = sellerRepository.existsBySellerPhone(sellerPhone);
        if (exists) {
            throw  new DuplicatePhoneException("Phone already exists");
        }
    }

    private void checkExistingUserName(String sellerUserName) {
        boolean exists = sellerRepository.existsBySellerUserName(sellerUserName);
        if (exists) {
            throw new DuplicateUsernameException("Username already in use");
        }
    }

    private void checkSellerAdult(LocalDate birthday) {
        LocalDate currentDate = LocalDate.now();


        Period period = Period.between(birthday, currentDate);
        int age = period.getYears();


        int adultAgeThreshold = 18;


        if (age < adultAgeThreshold) {
            throw new CustomerNotAdultException("Seller is not an adult");
        }
    }




}
