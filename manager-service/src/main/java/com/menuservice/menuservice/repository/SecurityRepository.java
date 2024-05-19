package com.menuservice.menuservice.repository;

import com.menuservice.menuservice.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Manager,Integer> {

    Optional<Manager> findByManagerName(String managerName);

}
