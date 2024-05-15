package com.gustavo.blogpessoal.service;

import com.gustavo.blogpessoal.DTO.PostDTO;
import com.gustavo.blogpessoal.entity.post.Post;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void createPost(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setUser(user);
        post.setContent(postDTO.content());
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Optional<Post> findByPostId(UUID id) {
        return postRepository.findById(id);
    }

}
