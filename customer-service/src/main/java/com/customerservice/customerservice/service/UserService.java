package com.customerservice.customerservice.service;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;

import java.util.List;

public interface UserService {
    List<ProductResponse> getAllMenu();

    Cart mapToCart(ProductResponse productResponse);

    CartResponse mapToCartResponse(Cart cart);

    void saveCart(Cart cart);

    List<Cart> getAllProductFromCart();

    void deleteProductFromCardById(int id);
}
