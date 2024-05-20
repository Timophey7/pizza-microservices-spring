package com.menuservice.menuservice.service;

import com.menuservice.menuservice.model.Product;
import com.menuservice.menuservice.model.ProductResponse;

import java.util.List;

public interface ProductService {

    void saveProduct(Product product);

    List<ProductResponse> getAllProducts();

    ProductResponse mapToResponse(Product product);

    void completeOrder(int id);
}
