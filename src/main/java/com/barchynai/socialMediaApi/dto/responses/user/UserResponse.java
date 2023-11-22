package com.barchynai.socialMediaApi.dto.responses.user;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String avatar
) {
}
