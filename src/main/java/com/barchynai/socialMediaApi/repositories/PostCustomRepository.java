package com.barchynai.socialMediaApi.repositories;

import com.barchynai.socialMediaApi.dto.responses.FindAllPostsResponse;
import com.barchynai.socialMediaApi.dto.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCustomRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Value("${cloud.aws.bucket.path}")
    private String path;

    public List<PostResponse> findAllByUser(Long userId) {

        return namedParameterJdbcTemplate.query("""
                        select p.id,
                               min(p.title) as title,
                               min(p.text) as text,
                               pic
                        from posts p
                        inner join (
                          select post_id, array_agg(concat(:path, pictures)) as pic
                          from post_pictures
                          group by post_id
                        ) pp on p.id = pp.post_id
                        inner join users u on p.user_id = u.id
                        where u.id = :userId
                        group by p.id, pic
                            """,
                new MapSqlParameterSource()
                        .addValue("path", path)
                        .addValue("userId", userId),
                (rs, rowNum) -> new PostResponse(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        List.of((String[]) rs.getArray(4).getArray())
                )
        );
    }
}
