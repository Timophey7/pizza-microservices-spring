package com.securityservice.repository;

import com.securityservice.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Transactional
    @Query(value = "update `pizza-security`.`pizza-user`" +
            "set email_verified = 1" +
            "where id = ?1", nativeQuery = true)
    void updateUser(Long userId);


}


