package com.securityservice.repository;

import com.securityservice.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setId(1L);
        user.setPassword("password");
        user.setFirstName("test");
        userRepository.save(user);

        User userOptional = userRepository.findUserByEmail("test@test.com").get();

        assertNotNull(userOptional);
        assertEquals(user, userOptional);

    }
}