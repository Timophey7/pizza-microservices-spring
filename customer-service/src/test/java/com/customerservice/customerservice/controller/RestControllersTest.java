package com.customerservice.customerservice.controller;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = RestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RestControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserServiceImpl userServiceImpl;

    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setId(1);
        cart.setProductName("pizza");
        cart.setProductPrice(100);
    }
    @Test
    void getAllOrders() throws Exception {
        List<Cart> cartList = List.of(cart);
        CartResponse cartResponse = CartResponse.builder()
                .id(1).productPrice(100)
                .productName("pizza").build();
        List<CartResponse> cartResponseList = List.of(cartResponse);

        when(userServiceImpl.getAllProductFromCart()).thenReturn(cartList);
        when(userServiceImpl.mapToCartResponse(cart)).thenReturn(cartResponse);

        ResultActions response = mockMvc.perform(get("/user/cartList"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cartResponseList)));

    }
}