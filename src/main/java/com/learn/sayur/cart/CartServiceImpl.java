package com.learn.sayur.cart;

import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem addCartItem(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItem -> new CartItemDTO(
                        cartItem.getId(),
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());
    }

//    @Override
//    public CartItem updateCartItem(Long itemId, int quantity) {
//        CartItem cartItem = cartItemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid cart item ID"));
//        cartItem.setQuantity(quantity);
//        return cartItemRepository.save(cartItem);
//    }

//    @Override
//    public void deleteCartItem(Long itemId) {
//        cartItemRepository.deleteById(itemId);
//    }
}