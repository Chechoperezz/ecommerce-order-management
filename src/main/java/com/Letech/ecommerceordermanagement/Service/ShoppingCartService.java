package com.Letech.ecommerceordermanagement.Service;


import com.Letech.ecommerceordermanagement.Entity.DTOS.CarItemDTO;
import com.Letech.ecommerceordermanagement.Entity.Items.CartItem;
import com.Letech.ecommerceordermanagement.Entity.Order;
import com.Letech.ecommerceordermanagement.Entity.ShoppingCart;
import com.Letech.ecommerceordermanagement.Entity.DTOS.ShoppingCartDTO;
import com.Letech.ecommerceordermanagement.Repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private KafkaTemplate<String,Object>kafkaTemplate;

    public ResponseEntity<CartItem> addCartItem(Long userId, CarItemDTO cartItemDTO) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Carrito de compras no encontrado"));

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(cartItemDTO.getProduct());
        cartItem.setQuantity(cartItemDTO.getQuantity());

        shoppingCart.getCartItems().add(cartItem);

        shoppingCartRepository.save(shoppingCart);

        return ResponseEntity.ok(cartItem);
    }


    public ResponseEntity<CartItem> reduceCartItemQuantity(Long userId, Long cartItemId, Integer quantityToReduce) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Carrito de compras no encontrado"));

        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem no encontrado"));

        if (cartItem.getQuantity() > quantityToReduce) {
            cartItem.setQuantity(cartItem.getQuantity() - quantityToReduce);
            shoppingCartRepository.save(shoppingCart);
            return ResponseEntity.ok(cartItem);
        } else if (cartItem.getQuantity().equals(quantityToReduce)) {
            shoppingCart.getCartItems().remove(cartItem);
            shoppingCartRepository.save(shoppingCart);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(cartItem);
        }
    }

    public ResponseEntity<Void> removeCartItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Carrito de compras no encontrado"));

        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem no encontrado"));

        shoppingCart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(shoppingCart);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Order> Checkout(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Carrito de compras no encontrado"));

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new RuntimeException("El carrito de compras está vacío");
        }

        ShoppingCartDTO shoppingCartDTO = createShoppingCartDTO(shoppingCart);
        kafkaTemplate.send("shopping-cart-checkout-topic", shoppingCartDTO);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return ResponseEntity.ok().build();
    }

    private ShoppingCartDTO createShoppingCartDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setAccountId(shoppingCart.getCustomer().getAccountId());

        List<CarItemDTO> cartItemDTOs = shoppingCart.getCartItems().stream()
                .map(cartItem -> {
                    CarItemDTO cartItemDTO = new CarItemDTO();
                    cartItemDTO.setProduct(cartItem.getProduct());
                    cartItemDTO.setQuantity(cartItem.getQuantity());
                    return cartItemDTO;
                })
                .collect(Collectors.toList());

        shoppingCartDTO.setCartItems(cartItemDTOs);

        return shoppingCartDTO;

    }
}

