package com.ecommerce.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.fashionshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}