package com.customerservice.customerservice.controller;



import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;
import com.customerservice.customerservice.service.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KafkaTemplate<String,CartResponse> kafkaTemplate;

    private final UserServiceImpl userServiceImpl;

    private static final String CUSTOMER_SERVICE = "customerService";

    @GetMapping("menuList")
    @CircuitBreaker(name = CUSTOMER_SERVICE,fallbackMethod = "fallbackMethod")
    public String menu(Model model){
        List<ProductResponse> allMenu = userServiceImpl.getAllMenu();
        model.addAttribute("allMenu",allMenu);
        return "menu";
    }

    @GetMapping("fallback")
    private String fallbackMethod(Exception ex){
        return "fallback";
    }


    @GetMapping("/addInCart/{productName}")
    public String addInCart(@PathVariable("productName")String productName){

        List<ProductResponse> allMenu = userServiceImpl.getAllMenu();
        for(ProductResponse productResponse : allMenu){
            if (productResponse.getProductName().equals(productName)){
                Cart cart = userServiceImpl.mapToCart(productResponse);
                CartResponse cartResponse = userServiceImpl.mapToCartResponse(cart);
                String key = String.valueOf(cartResponse.hashCode());
                kafkaTemplate.send(key,cartResponse);
                userServiceImpl.saveCart(cart);
            }
        }

        return "redirect:/user/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        List<Cart> allProductFromCart = userServiceImpl.getAllProductFromCart();
        List<CartResponse> cartResponses = allProductFromCart.stream()
                .map(m -> userServiceImpl.mapToCartResponse(m))
                .collect(Collectors.toList());
        model.addAttribute("carts", cartResponses);
        int resultPrice = cartResponses.stream().mapToInt(CartResponse::getProductPrice).sum();
        model.addAttribute("resultPrice", resultPrice);
        return "cart";


    }

    @GetMapping("/dummy")
    public String dummy(){
        return "redirect:/user/menuList";
    }


    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id")int id){

        userServiceImpl.deleteProductFromCardById(id);

        return "redirect:/user/cart";

    }

    @GetMapping("/createOrder")
    public String createOrder(){
        return "order";
    }





}
