package com.barchynai.socialMediaApi.dto.responses;

import java.util.List;

public record FindAllPostsResponse(
        Long id,
        String title,
        String text,
        String username,
        List<String> pictures

) {
}
