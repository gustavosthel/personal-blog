package com.gustavo.blogpessoal.controller;

import com.gustavo.blogpessoal.DTO.FeedDTO;
import com.gustavo.blogpessoal.DTO.PostDTO;
import com.gustavo.blogpessoal.confg.exception.ExceptionCustom;
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

import java.util.List;
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

    @GetMapping("/feed")
    public ResponseEntity<List<FeedDTO>> getPosts(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) throws Exception {
        List<FeedDTO> posts = postService.findAllPost(page, size);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) throws Exception {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Post> post = postService.findByPostId(id);

        if (post.isEmpty()) {
            throw new ExceptionCustom.PostNotFoundException();
        }

        if (!post.get().getUser().getUserId().equals(loggedInUser.getUserId())) {
            throw new ExceptionCustom.UserNotAuthorizeException();
        }

        postService.delete(post.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdminPost(@PathVariable UUID id) throws Exception {
        Optional<Post> post = postService.findByPostId(id);

        if (post.isEmpty()) {
            throw new ExceptionCustom.PostNotFoundException();
        }

        postService.delete(post.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
