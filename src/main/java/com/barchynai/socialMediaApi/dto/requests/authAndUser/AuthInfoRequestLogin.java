package com.barchynai.socialMediaApi.dto.requests.authAndUser;

public record AuthInfoRequestLogin(
        String username,
        String password
) {
}
