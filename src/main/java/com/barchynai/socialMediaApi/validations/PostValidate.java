package com.barchynai.socialMediaApi.validations;

import com.barchynai.socialMediaApi.models.Post;
import com.barchynai.socialMediaApi.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class PostValidate {

    private final PostRepository postRepository;

    public Post findById(Long postId) {

        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post is not found"));
    }
}
