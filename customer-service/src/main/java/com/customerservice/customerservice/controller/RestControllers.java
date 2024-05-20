package com.customerservice.customerservice.controller;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class RestControllers {


    private final UserServiceImpl userServiceImpl;


    @GetMapping("/cartList")
    public List<CartResponse> getAllOrders(){
        List<Cart> allProductFromCart = userServiceImpl.getAllProductFromCart();
        List<CartResponse> cartResponses = allProductFromCart.stream()
                .map(m -> userServiceImpl.mapToCartResponse(m))
                .collect(Collectors.toList());
        return cartResponses;
    }





}
