package com.gustavo.blogpessoal.repository;

import com.gustavo.blogpessoal.entity.post.Commented;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentedRepository extends JpaRepository<Commented, UUID> {
}
