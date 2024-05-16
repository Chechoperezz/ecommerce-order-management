package com.Letech.ecommerceordermanagement.Controller;
import com.Letech.ecommerceordermanagement.Entity.DTOS.CarItemDTO;
import com.Letech.ecommerceordermanagement.Entity.Items.CartItem;
import com.Letech.ecommerceordermanagement.Entity.Order;
import com.Letech.ecommerceordermanagement.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /*
    FRONT END: ES NECESARIO QUE LA ESTRUCTURA DEL JSON
    DE CADA SOLICITUD CUMPLA CON LOS ATRIBUTOS DE UN CARITEM
    BACK END RECIBE LAS SOLICITUDES EN FORMA DE CARITEMS
     */


    @PostMapping("/add-item")
    public ResponseEntity<CartItem> addCartItemToShoppingCart(@RequestBody CarItemDTO cartItemDTO,@RequestParam Long userId){

        return shoppingCartService.addCartItem(userId,cartItemDTO);
    }

    @PutMapping("/reduce-item-quantity")
    public ResponseEntity<CartItem> reduceCartItemQuantity(@RequestParam Long userId, @RequestParam Long cartItemId, @RequestParam Integer quantity) {
        return shoppingCartService.reduceCartItemQuantity(userId, cartItemId, quantity);
    }

    @DeleteMapping("/remove-item")
    public ResponseEntity<Void> removeCartItem(@RequestParam Long userId, @RequestParam Long cartItemId) {
        return shoppingCartService.removeCartItem(userId, cartItemId);
    }

    @PostMapping("/place-order")
    public ResponseEntity<Order> CheckOut(@RequestParam Long userId){
        return shoppingCartService.Checkout(userId);

    }




}

