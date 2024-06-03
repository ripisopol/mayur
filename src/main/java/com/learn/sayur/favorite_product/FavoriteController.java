package com.learn.sayur.favorite_product;

import com.learn.sayur.product.entity.Product;
import com.learn.sayur.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorite-products")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/toggle")
    public ResponseEntity<Response<String>> toggleFavoriteProduct(@RequestBody ToggleFavoriteRequest request) {
        Long productId = request.getProductId();
        if (productId == null) {
            return Response.failedResponse(HttpStatus.BAD_REQUEST.value(), "Product ID is required");
        }

        boolean isFavorite = favoriteService.toggleFavoriteProduct(productId);
        String message = isFavorite ? "Product added to favorites" : "Product removed from favorites";
        return Response.successfulResponse(message);
    }


    @GetMapping
    public ResponseEntity<Response<List<Product>>> getFavoriteProducts() {
        List<Product> favoriteProducts = favoriteService.getFavoriteProducts();
        return Response.successfulResponse("Favorite products retrieved successfully", favoriteProducts);
    }
}
