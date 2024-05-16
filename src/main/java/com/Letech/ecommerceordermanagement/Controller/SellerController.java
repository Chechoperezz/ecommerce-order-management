package com.Letech.ecommerceordermanagement.Controller;
import com.Letech.ecommerceordermanagement.Entity.Seller;
import com.Letech.ecommerceordermanagement.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sellers")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerSeller(@RequestBody Seller seller){
        sellerService.saveSeller(seller);
        return ResponseEntity.ok("Registration Succesful");

    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<String> updateSeller(@PathVariable("id") Long sellerId,
                                               @RequestBody Seller seller){
        sellerService.updateSeller(sellerId,seller);
        return ResponseEntity.ok("Update Successful");
    }

    @PutMapping("/update/id/{id}/password/{password}")
    public ResponseEntity<String> updatePassword(
            @PathVariable("id") Long sellerId,
            @PathVariable("password") String newPassword){
        sellerService.updatePassword(sellerId,newPassword);
        return ResponseEntity.ok("Password Changed Successfully");

    }


}