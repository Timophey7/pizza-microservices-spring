package com.customerservice.customerservice.repositroy;

import com.customerservice.customerservice.models.Cart;
import jakarta.persistence.Access;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void findByProductName() {
        Cart cart = new Cart();
        cart.setProductName("test");
        cart.setId(1);
        cartRepository.save(cart);

        Cart test = cartRepository.findByProductName("test").get();

        assertEquals(cart, test);

    }
}