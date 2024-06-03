package com.learn.sayur.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAll();
//    List<CartItem> findCartItemsById(Long id);

    @Query("SELECT new com.learn.sayur.cart.CartItemDTO(ci.id, ci.productId, ci.quantity) FROM CartItem ci")
    List<CartItemDTO> findAllCartItemsDTO();
}
