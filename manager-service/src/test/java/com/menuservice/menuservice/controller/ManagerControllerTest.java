package com.menuservice.menuservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menuservice.menuservice.model.Order;
import com.menuservice.menuservice.model.Product;
import com.menuservice.menuservice.service.impl.OrderServiceImpl;
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
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ManagerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OrderServiceImpl orderService;

    @MockBean
    ProductServiceImpl productServiceImpl;

    @MockBean
    BindingResult bindingResult;

    @Test
    void infoForManagers() throws Exception {
        ResultActions response = mockMvc.perform(get("/manager/tools/"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addProduct() throws Exception {
        Product product = new Product();

        ResultActions response = mockMvc.perform(get("/manager/tools/addProduct"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("product", product));
    }

    @Test
    void saveProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setProductName("pizza");
        when(bindingResult.hasErrors()).thenReturn(false);

        productServiceImpl.saveProduct(product);

        ResultActions response = mockMvc.perform(post("/manager/tools/saveProduct"));

        response.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        verify(productServiceImpl).saveProduct(product);


    }

    @Test
    void ordersList() throws Exception {
        Order order = new Order();
        order.setOrderId(1);
        order.setProductName("pizza");
        order.setProductPrice(100);
        List<Order> orders = List.of(order);

        when(orderService.findAllOrders()).thenReturn(orders);

        ResultActions response = mockMvc.perform(get("/manager/tools/orders"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("orders", orders));
    }
}