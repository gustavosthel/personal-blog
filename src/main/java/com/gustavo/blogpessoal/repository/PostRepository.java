package com.gustavo.blogpessoal.repository;

import com.gustavo.blogpessoal.entity.post.Post;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Optional<Post> findById(UUID id);
}
