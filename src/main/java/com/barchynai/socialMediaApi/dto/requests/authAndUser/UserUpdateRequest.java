package com.barchynai.socialMediaApi.dto.requests.authAndUser;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String avatar,
        AuthInfoUpdateRequest authInfoUpdateRequest
) {
}
