package com.learn.sayur.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartItem> addCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addCartItem(productId, quantity));
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartService.getAllCartItems());
    }

//    @PutMapping("/{itemId}")
//    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
//        return ResponseEntity.ok(cartService.updateCartItem(itemId, quantity));
//    }

//    @DeleteMapping("/{itemId}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
//        cartService.deleteCartItem(itemId);
//        return ResponseEntity.noContent().build();
//    }
}