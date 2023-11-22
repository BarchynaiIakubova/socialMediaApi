package com.barchynai.socialMediaApi.api;


import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.services.PostFriendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAuthority('USER')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/postFriend")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "PostFriend API", description = "PostFriend endpoints")
public class PostFriendApi {

    private final PostFriendService postFriendService;


    @PostMapping("/{receiverId}/sendRequest")
    public Response sendFriendRequest(@PathVariable Long receiverId ) {

        return postFriendService.sendFriendRequest(receiverId);
    }

    @PostMapping("/receiveRequest")
    public Response receiveFriendRequest(@RequestParam Long requestedId) {

        return postFriendService.receiveFriendRequest(requestedId);
    }

    @PostMapping("/rejectRequest")
    public Response rejectFriendRequest(@RequestParam Long requestedId) {

        return postFriendService.rejectFriendRequest(requestedId);
    }
}
