package com.barchynai.socialMediaApi.dto.requests.authAndUser;

public record AuthInfoUpdateRequest(
        String username,
        String email,
        String oldPassword,
        String newPassword
) {
}
