package com.learn.sayur.cart;

import com.learn.sayur.exception.DataNotFoundException;
import com.learn.sayur.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Response<CartItemDTO>> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO addedCartItem = cartService.addCartItem(cartItemDTO);
            return Response.successfulResponse("Cart item added successfully", addedCartItem);
        } catch (DataNotFoundException e) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<CartItemDTO>>> getAllCartItems() {
        List<CartItemDTO> cartItems = cartService.getAllCartItems();
        return cartItems.isEmpty() ?
                Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Cart is empty") :
                Response.successfulResponse("Cart items retrieved successfully", cartItems);
    }

    @PutMapping("{ItemId}")
    public ResponseEntity<Response<CartItemDTO>> updateCartItem(@PathVariable("ItemId") Long itemId, @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO updatedCartItem = cartService.updateCartItem(itemId, cartItemDTO.getQuantity());
            return Response.successfulResponse("Cart item updated successfully", updatedCartItem);
        } catch (DataNotFoundException e) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("{ItemId}")
    public ResponseEntity<Response<Void>> deleteCartItem(@PathVariable("ItemId") Long itemId) {
        try {
            cartService.deleteCartItem(itemId);
            return Response.successfulResponse("Cart item deleted successfully");
        } catch (DataNotFoundException e) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
