package com.gustavo.blogpessoal.service;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.gustavo.blogpessoal.DTO.CommentedDTO;
import com.gustavo.blogpessoal.entity.post.Commented;
import com.gustavo.blogpessoal.entity.post.Post;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.repository.CommentedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentedService {

    @Autowired
    private CommentedRepository commentedRepository;

    public void createCommented(CommentedDTO comment, Post post, User user) {
        Commented commented = new Commented();
        commented.setComment(comment.commented());
        commented.setPost(post);
        commented.setUser(user);
        commented.setCommentTime(LocalDateTime.now());
        post.getCommented().add(commented);
        commentedRepository.save(commented);
    }

    public Optional<Commented> findByCommentId(UUID id) {
        return commentedRepository.findById(id);
    }

    public void delete(Commented commented) {
       commentedRepository.delete(commented);
    }
}
