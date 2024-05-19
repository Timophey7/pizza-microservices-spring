package com.customerservice.customerservice.controller;



import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;
import com.customerservice.customerservice.service.UserService;
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

    private final UserService userService;

    private static final String CUSTOMER_SERVICE = "customerService";

    @GetMapping("menuList")
    @CircuitBreaker(name = CUSTOMER_SERVICE,fallbackMethod = "fallbackMethod")
    public String menu(Model model){
        List<ProductResponse> allMenu = userService.getAllMenu();
        model.addAttribute("allMenu",allMenu);
        return "menu";
    }

    @GetMapping("fallback")
    private String fallbackMethod(Exception ex){
        return "fallback";
    }


    @GetMapping("/addInCart/{productName}")
    public String addInCart(@PathVariable("productName")String productName){

        List<ProductResponse> allMenu = userService.getAllMenu();
        for(ProductResponse productResponse : allMenu){
            if (productResponse.getProductName().equals(productName)){
                Cart cart = userService.mapToCart(productResponse);
                CartResponse cartResponse = userService.mapToCartResponse(cart);
                String key = String.valueOf(cartResponse.hashCode());
                kafkaTemplate.send(key,cartResponse);
                userService.saveCart(cart);
            }
        }

        return "redirect:/user/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model){
        List<Cart> allProductFromCart = userService.getAllProductFromCart();
        List<CartResponse> cartResponses = allProductFromCart.stream()
                .map(m -> userService.mapToCartResponse(m))
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

        userService.deleteProductFromCardById(id);

        return "redirect:/user/cart";

    }

    @GetMapping("/createOrder")
    public String createOrder(){
        return "order";
    }





}
