package com.menuservice.menuservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menuservice.menuservice.model.ProductResponse;
import com.menuservice.menuservice.service.impl.ProductServiceImpl;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MenuControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ProductServiceImpl productServiceImpl;

    @Test
    void getAllProducts() throws Exception {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName("pizza");
        productResponse.setPrice(100);
        List<ProductResponse> productResponseList = List.of(productResponse);

        when(productServiceImpl.getAllProducts()).thenReturn(productResponseList);

        ResultActions response = mockMvc.perform(get("/menu/getAllMenu"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(productResponseList)));

    }
}