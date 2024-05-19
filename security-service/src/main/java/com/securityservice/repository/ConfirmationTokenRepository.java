package com.securityservice.repository;

import com.securityservice.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query(value = "SELECT * FROM `pizza-security`.tokens where user_email = ?1;",nativeQuery = true)
    ConfirmationToken findByUserEmail(String email);

}
