package com.menuservice.menuservice.controller;

import com.menuservice.menuservice.model.ProductResponse;
import com.menuservice.menuservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final ProductService productService;

    @GetMapping("/getAllMenu")
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> productServiceAllProducts = productService.getAllProducts();
        return productServiceAllProducts;
    }


}
