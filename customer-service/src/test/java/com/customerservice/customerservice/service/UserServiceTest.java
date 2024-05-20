package com.customerservice.customerservice.service;

import com.customerservice.customerservice.models.Cart;
import com.customerservice.customerservice.models.CartResponse;
import com.customerservice.customerservice.models.ProductResponse;
import com.customerservice.customerservice.repositroy.CartRepository;
import com.customerservice.customerservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    WebClient.Builder webClientBuilder;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setProductName("pizza");
        cart.setProductPrice(100);
    }

    @Test
    void getAllMenu() {
        ProductResponse productResponse = ProductResponse.builder()
                .productName("pizza").price(100).build();
        List<ProductResponse> expectedMenu = List.of(productResponse);

        WebClient mockWebClient = Mockito.mock(WebClient.class);
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient.RequestHeadersUriSpec mockUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec mockResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(mockWebClient.get()).thenReturn(mockUriSpec);
        when(mockUriSpec.uri("http://manager-service/menu/getAllMenu")).thenReturn(mockUriSpec);
        when(mockUriSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(ProductResponse[].class)).thenReturn(Mono.just(expectedMenu.toArray(new ProductResponse[0])));

        when(webClientBuilder.build()).thenReturn(mockWebClient);

        List<ProductResponse> actualMenu = userServiceImpl.getAllMenu();

        assertEquals(expectedMenu, actualMenu);


    }

    @Test
    void mapToCart() {
        ProductResponse productResponse = ProductResponse.builder()
                .productName("pizza").price(100).build();
        Cart mapToCart = userServiceImpl.mapToCart(productResponse);

        assertEquals(cart, mapToCart);
    }

    @Test
    void mapToCartResponse() {
        CartResponse cartResponse = CartResponse.builder()
                .productName("pizza").productPrice(100).build();

        CartResponse mappedToCartResponse = userServiceImpl.mapToCartResponse(cart);
        assertEquals(cartResponse, mappedToCartResponse);
    }


    @Test
    void getAllProductFromCart() {
        List<Cart> cartList = List.of(cart);
        when(cartRepository.findAll()).thenReturn(cartList);

        List<Cart> allProductFromCart = userServiceImpl.getAllProductFromCart();

        assertEquals(cartList, allProductFromCart);
        verify(cartRepository).findAll();
    }

}