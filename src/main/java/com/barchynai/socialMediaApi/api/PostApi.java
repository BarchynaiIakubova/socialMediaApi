package com.barchynai.socialMediaApi.api;

import com.barchynai.socialMediaApi.dto.requests.PostRequest;
import com.barchynai.socialMediaApi.dto.responses.FindAllPostsResponse;
import com.barchynai.socialMediaApi.dto.responses.PostResponse;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "Post API", description = "Post endpoints")
public class PostApi {

    private final PostService postService;

    @PostMapping
    Response save(@RequestBody PostRequest postRequest, @RequestParam List<String> pictures) {

        return postService.save(postRequest, pictures);
    }

    @DeleteMapping("/{postId}")
    Response delete(@PathVariable Long postId) {

        return postService.deleteByPostId(postId);
    }

    @PutMapping("/{postId}")
    Response update(@PathVariable Long postId,
                    @RequestBody PostRequest postRequest,
                    @RequestParam List<String> pictures) {

        return postService.update(postId, postRequest, pictures);
    }

    @GetMapping("/{postId}")
    PostResponse findById(@PathVariable Long postId) {

        return postService.findById(postId);
    }

    @GetMapping("/{userId})")
    List<PostResponse> findAll(@PathVariable Long userId) {

        return postService.findAllByUser(userId);
    }
}
/*

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        List<Word> words = List.of(
           Word.of("large", -3),
           Word.of("phone", -6),
           Word.of("newspaper", 2),
           Word.of("chocolate", -10),
           Word.of("connection", 13),
           Word.of("engineering", 10)
        );

        System.out.println(words.stream()
                .filter(Predicate.not(Word::isBad))
                .max(Comparator.comparing(Word::getPriority))
                .map(Word::getValue)
                .orElse("Nothing found"));
    }

    @AllArgsConstructor
    @Getter
    public static class Word {

        private final String value;
        private final int priority;

        static Word of(String value, int priority) {
            return new Word(value, priority);
        }
        public boolean isBad() {
            return this.value.contains("la");
        }
    }
}

 */