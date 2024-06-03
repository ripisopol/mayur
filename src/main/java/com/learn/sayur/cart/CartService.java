package com.learn.sayur.cart;

import java.util.List;

public interface CartService {
    CartItemDTO addCartItem(CartItemDTO cartItemDTO);

    List<CartItemDTO> getAllCartItems();

    CartItemDTO updateCartItem(Long itemId, int quantity);

    void deleteCartItem(Long itemId);

//    CartItemDTO getCartItemById(Long id); // New method
}
