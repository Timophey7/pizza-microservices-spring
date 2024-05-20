package com.menuservice.menuservice.service;

import com.menuservice.menuservice.model.CartResponse;
import com.menuservice.menuservice.model.Product;
import com.menuservice.menuservice.model.ProductResponse;
import com.menuservice.menuservice.repository.ProductRepository;
import com.menuservice.menuservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    WebClient.Builder webClientBuilder;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    ProductResponse productResponse;
    Product product;
    @BeforeEach
    void setUp() {
        productResponse = new ProductResponse();
        productResponse.setProductName("pizza");
        productResponse.setPrice(100);
        product = new Product();
        product.setProductName("pizza");
        product.setPrice(100);
    }


    @Test
    void getAllProducts() {
        List<Product> products = List.of(product);
        List<ProductResponse> productResponseList = List.of(productResponse);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> allProducts = productServiceImpl.getAllProducts();

        assertEquals(productResponseList, allProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void mapToResponse(){
        ProductResponse productResponse1 = productServiceImpl.mapToResponse(product);

        assertEquals(productResponse, productResponse1);
    }

    @Test
    void completeOrder(){
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec request = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        CartResponse cartResponse = new CartResponse();

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(request);
        when(request.uri("http://user-service/user/deleteProduct/" + 1)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CartResponse.class)).thenReturn(Mono.just(cartResponse));


        productServiceImpl.completeOrder(1);

        verify(webClientBuilder, times(1)).build();
        verify(webClient, times(1)).get();
        verify(request, times(1)).uri("http://user-service/user/deleteProduct/" + 1);
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(CartResponse.class);
    }

}