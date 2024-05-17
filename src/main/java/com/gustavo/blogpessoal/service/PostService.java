package com.gustavo.blogpessoal.service;

import com.gustavo.blogpessoal.DTO.FeedDTO;
import com.gustavo.blogpessoal.DTO.PostDTO;
import com.gustavo.blogpessoal.entity.post.Post;
import com.gustavo.blogpessoal.entity.user.User;
import com.gustavo.blogpessoal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;;

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

    public List<FeedDTO> findAllPost(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage  = postRepository.findAll(pageable);
        return postPage.getContent().stream()
                .map(FeedDTO::fromPost)
                .collect(Collectors.toList());
    }

    public Optional<Post> findByPostId(UUID id) {
        return postRepository.findById(id);
    }

}
