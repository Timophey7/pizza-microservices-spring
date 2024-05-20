package com.customerservice.customerservice.controller;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;
import com.customerservice.customerservice.repositroy.CartRepository;
import com.customerservice.customerservice.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    KafkaTemplate<String, CartResponse> kafkaTemplate;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    UserServiceImpl userServiceImpl;

    @Mock
    private WebClient.Builder webClientBuilder;

    Cart cart;


    @BeforeEach
    void setUp() {
        cart  = new Cart();
        cart.setProductPrice(100);
        cart.setProductName("pizza");
    }

    @Test
    void menu() throws Exception {
        ProductResponse productResponse = ProductResponse.builder().price(100).productName("pizza").build();
        List<ProductResponse> productResponseList = List.of(productResponse);
        when(userServiceImpl.getAllMenu()).thenReturn(productResponseList);

        ResultActions response = mockMvc.perform(get("/user/menuList"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("allMenu", productResponseList));

    }

    @Test
    void addInCart() throws Exception {
        String productName = "pizza";
        CartResponse cartResponse = CartResponse.builder().productName("pizza").productPrice(100).build();
        ProductResponse productResponse = ProductResponse.builder()
                .price(100).productName("pizza").build();
        List<ProductResponse> productResponseList = List.of(productResponse);

        when(userServiceImpl.getAllMenu()).thenReturn(productResponseList);
        when(userServiceImpl.mapToCart(productResponse)).thenReturn(cart);
        when(userServiceImpl.mapToCartResponse(cart)).thenReturn(cartResponse);

        ResultActions response = mockMvc.perform(get("/user/addInCart/" + productName));

        response.andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    void cart() throws Exception {
        List<Cart> cartList = List.of(cart);
        CartResponse cartResponse = CartResponse.builder().productPrice(100).productName("pizza").build();
        List<CartResponse> cartResponseList = List.of(cartResponse);
        int resultPrice = 100;

        when(userServiceImpl.getAllProductFromCart()).thenReturn(cartList);
        when(userServiceImpl.mapToCartResponse(any(Cart.class))).thenReturn(cartResponse);


        ResultActions response = mockMvc.perform(get("/user/cart"));

        response.andExpect(status().isOk())
                .andExpect(model().attribute("carts", cartResponseList))
                .andExpect(model().attribute("resultPrice", resultPrice));

    }

    @Test
    void dummy() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/dummy"));

        response.andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteProduct() throws Exception {
        int id = 1;

        ResultActions response = mockMvc.perform(get("/user/deleteProduct/" + id));

        response.andExpect(status().is3xxRedirection());
    }

    @Test
    void createOrder() throws Exception {
        ResultActions response = mockMvc.perform(get("/user/createOrder"));
        response.andExpect(status().isOk());
    }
}