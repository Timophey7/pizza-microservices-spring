package com.menuservice.menuservice.controller;

import com.menuservice.menuservice.model.ProductResponse;
import com.menuservice.menuservice.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/getAllMenu")
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> productServiceAllProducts = productServiceImpl.getAllProducts();
        return productServiceAllProducts;
    }


}
