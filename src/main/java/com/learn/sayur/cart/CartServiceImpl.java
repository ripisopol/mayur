package com.learn.sayur.cart;

import com.learn.sayur.exception.DataNotFoundException;
import com.learn.sayur.product.entity.Product;
import com.learn.sayur.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO) {
        Long productId = cartItemDTO.getProductId();
        int quantity = cartItemDTO.getQuantity();

        Cart cart = cartRepository.findById(1L).orElseGet(() -> {
            Cart newCart = new Cart();
            return cartRepository.save(newCart);
        });

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return item;
                })
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setProductId(productId);
                    newItem.setQuantity(quantity);
                    newItem.setCart(cart);
                    return newItem;
                });

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return new CartItemDTO(savedCartItem.getId(), savedCartItem.getProductId(), savedCartItem.getQuantity());
    }

    @Override
    public CartItemDTO updateCartItem(Long itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new DataNotFoundException("Invalid cart item ID"));
        cartItem.setQuantity(quantity);
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return new CartItemDTO(updatedCartItem.getId(), updatedCartItem.getProductId(), updatedCartItem.getQuantity());
    }

    @Override
    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.isEmpty() ? Collections.emptyList() :
                cartItems.stream()
                        .map(item -> new CartItemDTO(item.getId(), item.getProductId(), item.getQuantity()))
                        .collect(Collectors.toList());
    }

    @Override
    public void deleteCartItem(Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new DataNotFoundException("Invalid cart item ID"));
        cartItemRepository.deleteById(itemId);
    }
}
