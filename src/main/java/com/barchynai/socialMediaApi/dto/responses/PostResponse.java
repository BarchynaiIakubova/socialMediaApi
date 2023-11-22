package com.barchynai.socialMediaApi.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String text;
    private List<String> pictures;

    public PostResponse(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }
}