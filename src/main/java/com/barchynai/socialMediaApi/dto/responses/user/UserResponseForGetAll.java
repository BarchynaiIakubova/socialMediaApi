package com.barchynai.socialMediaApi.dto.responses.user;

public record UserResponseForGetAll(
        Long id,
        String fullName,
        String avatar
) {
}
