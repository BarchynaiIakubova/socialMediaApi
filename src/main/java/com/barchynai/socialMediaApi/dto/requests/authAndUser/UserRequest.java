package com.barchynai.socialMediaApi.dto.requests.authAndUser;

public record UserRequest(
        String firstName,
        String lastName,
        AuthInfoRequest authInfoRequest
) {
}
