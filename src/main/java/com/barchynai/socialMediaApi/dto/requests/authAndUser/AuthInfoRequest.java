package com.barchynai.socialMediaApi.dto.requests.authAndUser;

public record AuthInfoRequest(
        String username,
        String email,
        String password
) {
}
