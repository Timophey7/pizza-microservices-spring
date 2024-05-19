package com.customerservice.customerservice.controller;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class RestControllers {


    private final UserService userService;


    @GetMapping("/cartList")
    public List<CartResponse> getAllOrders(){
        List<Cart> allProductFromCart = userService.getAllProductFromCart();
        List<CartResponse> cartResponses = allProductFromCart.stream()
                .map(m -> userService.mapToCartResponse(m))
                .collect(Collectors.toList());
        return cartResponses;
    }





}
