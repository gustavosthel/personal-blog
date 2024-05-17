package com.gustavo.blogpessoal.controller;

import com.gustavo.blogpessoal.DTO.CommentedDTO;
import com.gustavo.blogpessoal.confg.exception.ExceptionCustom;
import com.gustavo.blogpessoal.entity.post.Commented;
import com.gustavo.blogpessoal.entity.post.Post;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.service.CommentedService;
import com.gustavo.blogpessoal.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/commented")
public class CommentedController {

    @Autowired
    private CommentedService commentedService;
    @Autowired
    private PostService postService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Void> createCommented(@RequestBody CommentedDTO commented,@PathVariable UUID id) throws Exception {
        Optional<Post> postOptional = postService.findByPostId(id);

        if (postOptional.isEmpty()) {
            throw new ExceptionCustom.PostNotFoundException();
        }

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postOptional.get();
        commentedService.createCommented(commented, post, loggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommented(@PathVariable UUID id) throws Exception {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Commented> commented = commentedService.findByCommentId(id);

        if (commented.isEmpty()) {
            throw new ExceptionCustom.CommentedNotFoundException();
        }

        if (!commented.get().getUser().getUserId().equals(loggedInUser.getUserId())) {
            throw new ExceptionCustom.UserNotAuthorizeException();
        }

        commentedService.delete(commented.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/youPost/delete/{id}")
    public ResponseEntity<Void> deleteCommentInYouPost(@PathVariable UUID id) throws Exception {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Commented> commented = commentedService.findByCommentId(id);

        if (commented.isEmpty()) {
            throw new ExceptionCustom.CommentedNotFoundException();
        }

        if (!commented.get().getPost().getUser().getUserId().equals(loggedInUser.getUserId())) {
            throw new ExceptionCustom.UserNotAuthorizeException();
        }

        commentedService.delete(commented.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}