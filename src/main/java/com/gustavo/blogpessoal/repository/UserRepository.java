package com.gustavo.blogpessoal.repository;

import com.gustavo.blogpessoal.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
