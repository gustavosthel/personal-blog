package com.gustavo.blogpessoal.repository;

import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.entity.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserType(UserType type);

    Optional<User> findById(UUID id);

}
