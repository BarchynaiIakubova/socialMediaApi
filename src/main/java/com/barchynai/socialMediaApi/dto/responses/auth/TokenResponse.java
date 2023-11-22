package com.barchynai.socialMediaApi.dto.responses.auth;

import com.barchynai.socialMediaApi.enums.Role;

public record TokenResponse(
        String token,
        Role role
) {
}
