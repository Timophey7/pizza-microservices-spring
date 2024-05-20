package com.menuservice.menuservice.service.impl;

import com.menuservice.menuservice.model.CartResponse;
import com.menuservice.menuservice.model.Product;
import com.menuservice.menuservice.model.ProductResponse;
import com.menuservice.menuservice.repository.ProductRepository;
import com.menuservice.menuservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final WebClient.Builder webClient;

    private final ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> productResponses = productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return productResponses;
    }

    public ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(product.getProductName());
        productResponse.setDescriptionOfProduct(product.getDescriptionOfProduct());
        productResponse.setPrice(product.getPrice());
        productResponse.setProductWeight(product.getProductWeight());
        productResponse.setPhotoUrl(product.getPhotoUrl());
        return productResponse;
    }




    public void completeOrder(int id){
        webClient.build().get().uri("http://user-service/user/deleteProduct/"+id).retrieve().bodyToMono(CartResponse.class).block();
    }

}
