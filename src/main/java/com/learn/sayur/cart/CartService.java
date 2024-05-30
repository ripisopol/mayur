package com.learn.sayur.cart;

import com.learn.sayur.product.entity.Product;

import java.util.List;

public interface CartService {
    CartItem addCartItem(Long productId, int quantity);
    List<CartItemDTO> getAllCartItems();
//    CartItem updateCartItem(Long itemId, int quantity);
//    void deleteCartItem(Long itemId);
}
