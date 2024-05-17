package com.gustavo.blogpessoal.DTO;

import com.gustavo.blogpessoal.entity.post.Post;

public record FeedDTO(Post post){

    public static FeedDTO fromPost(Post post) {
        return new FeedDTO(post);
    }
}
