package com.barchynai.socialMediaApi.api.user;

import com.barchynai.socialMediaApi.dto.requests.authAndUser.UserRequest;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.UserUpdateRequest;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.dto.responses.auth.TokenResponse;
import com.barchynai.socialMediaApi.dto.responses.user.UserResponse;
import com.barchynai.socialMediaApi.dto.responses.user.UserResponseForGetAll;
import com.barchynai.socialMediaApi.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "User API", description = "User endpoints")
public class UserApi {

    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Register user", description = "This method for registration users")
    TokenResponse registrationUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @GetMapping("/pagination")
    @Operation(summary = "Get all users", description = "This method for getting all users by their id")
    List<UserResponseForGetAll> findAllUsers(@RequestParam(required = false) String search,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return userService.findAllUsers(search, page, size);
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "Get user by id", description = "This method gets user by id")
    UserResponse findUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "This method updates user by id")
    Response updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {

        return userService.updateUser(userId, userUpdateRequest);
    }


}
