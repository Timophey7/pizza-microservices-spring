package com.customerservice.customerservice.service.impl;


import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;
import com.customerservice.customerservice.repositroy.CartRepository;
import com.customerservice.customerservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final CartRepository cartRepository;


    private final WebClient.Builder webClient;


    public List<ProductResponse> getAllMenu(){
        ProductResponse[] productResponses = webClient.build()
                .get().uri("http://manager-service/menu/getAllMenu")
                .retrieve().bodyToMono(ProductResponse[].class).block();
        List<ProductResponse> productResponseList = Arrays.stream(productResponses).collect(Collectors.toList());
        return productResponseList;
    }

    public Cart mapToCart(ProductResponse productResponse){
        Cart cart = new Cart();
        cart.setProductName(productResponse.getProductName());
        cart.setProductPrice(productResponse.getPrice());
        cart.setProductUrl(productResponse.getPhotoUrl());
        return cart;
    }

    public CartResponse mapToCartResponse(Cart cart){
        CartResponse cartResponse = CartResponse.builder().build();
        cartResponse.setProductName(cart.getProductName());
        cartResponse.setProductPrice(cart.getProductPrice());
        cartResponse.setProductUrl(cart.getProductUrl());
        cartResponse.setId(cart.getId());
        return cartResponse;
    }

    public void saveCart(Cart cart){
        try {
            cartRepository.save(cart);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    public List<Cart> getAllProductFromCart(){
        return cartRepository.findAll();
    }

    public void deleteProductFromCardById(int id){
        try {
            cartRepository.deleteById(id);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

}
