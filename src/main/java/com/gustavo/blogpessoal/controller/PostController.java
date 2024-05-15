package com.gustavo.blogpessoal.controller;

import com.gustavo.blogpessoal.DTO.PostDTO;
import com.gustavo.blogpessoal.entity.post.Post;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostDTO post) throws Exception {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.createPost(post, loggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) throws Exception {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Post> post = postService.findByPostId(id);

        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!post.get().getUser().getUserId().equals(loggedInUser.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postService.delete(post.get());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdminPost(@PathVariable UUID id) throws Exception {
        Optional<Post> post = postService.findByPostId(id);

        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        postService.delete(post.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
