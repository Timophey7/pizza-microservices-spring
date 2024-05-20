package com.menuservice.menuservice.repository;

import com.menuservice.menuservice.model.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SecurityRepositoryTest {

    @Autowired
    private SecurityRepository securityRepository;

    @Test
    void findByManagerName() {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setManagerName("test");
        manager.setEmail("test@test.com");
        manager.setPassword("test");

        securityRepository.save(manager);

        Manager test = securityRepository.findByManagerName("test").get();
        assertEquals(manager, test);
    }
}