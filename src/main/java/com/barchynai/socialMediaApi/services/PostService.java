package com.barchynai.socialMediaApi.services;

import com.barchynai.socialMediaApi.dto.requests.PostRequest;
import com.barchynai.socialMediaApi.dto.responses.FindAllPostsResponse;
import com.barchynai.socialMediaApi.dto.responses.PostResponse;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.models.Post;
import com.barchynai.socialMediaApi.models.users.User;
import com.barchynai.socialMediaApi.repositories.PostCustomRepository;
import com.barchynai.socialMediaApi.repositories.PostRepository;
import com.barchynai.socialMediaApi.validations.PostValidate;
import com.barchynai.socialMediaApi.validations.UserValidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostValidate postValidate;
    private final S3Service s3Service;
    private final UserValidate userValidate;
    private final PostCustomRepository postCustomRepository;

    @Value("${cloud.aws.bucket.path}")
    private String path;

    public Response save(PostRequest postRequest, List<String> pictures) {


        Post post = Post.builder()
                .title(postRequest.title())
                .text(postRequest.text())
                .user(userValidate.getByAuthentication())
                .pictures(pictures.stream().map(picture -> picture.substring(path.length())).toList())
                .build();

        postRepository.save(post);

        return new Response("Post successfully saved");
    }

    public Response deleteByPostId(Long postId) {

        List<String> pictureLinks = postRepository.findAllPicturesByPostId(postId, path);

        log.info("findAllPicturesByPostId works");

        postRepository.deletePicturesByPostId(postId);
        log.info("deletePicturesByPostId works");

        postRepository.deleteByPostId(postId);
        log.info("deleteByPostId works");

        for (String link: pictureLinks) {

            s3Service.delete(link.substring(path.length()));
        }
        log.info("s3 delete works");
//        Post post = postValidate.findById(postId);

//        postCustomRepository.deleteByPostId(postId);

        return new Response("Post successfully removed");
    }

    @Transactional
    public Response update(Long postId, PostRequest postRequest, List<String> pictures) {

        Post post = postValidate.findById(postId);

        post.setTitle(postRequest.title());
        post.setText(postRequest.text());


        List<String> updatedPictures = new ArrayList<>(pictures);
        post.setPictures(updatedPictures);

//        postRepository.save(post);



        return new Response("Post has been successfully updated");
    }

    public PostResponse findById(Long postId) {

        Post post = postValidate.findById(postId);

        return new PostResponse(
                postId,
                post.getTitle(),
                post.getText(),
                post.getPictures()
        );
    }

    public List<PostResponse> findAllByUser(Long userId) {

//        List<PostResponse> postResponses = postRepository.findAllByUser(userId);
//
//        for (PostResponse postResponse : postResponses) {
//
//            List<String> pictures = postRepository.findAllPicturesByPostId(postResponse.getId(), path);
//
//            postResponse.setPictures(pictures);
//        }
//        return postResponses;
        return postCustomRepository.findAllByUser(userId);
    }
}
