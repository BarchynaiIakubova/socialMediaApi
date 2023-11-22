package com.barchynai.socialMediaApi.repositories;

import com.barchynai.socialMediaApi.dto.responses.PostResponse;
import com.barchynai.socialMediaApi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select new com.barchynai.socialMediaApi.dto.responses.PostResponse(
            p.id,
            p.title,
            p.text
            ) from Post p
            inner join p.user u
            where u.id = :userId
            """)
    List<PostResponse> findAllByUser(Long userId);

    @Query(value = """
           select concat(:path, pp.pictures)
           from post_pictures pp
           where pp.post_id = :postId
           """, nativeQuery = true)
    List<String> findAllPicturesByPostId(Long postId, String path);

    @Modifying
    @Transactional
    @Query("""
           delete from Post p
           where p.id = :postId
           """)
    void deleteByPostId(Long postId);

    @Modifying
    @Transactional
    @Query(value = """
           delete from post_pictures pp
           where pp.post_id = :postId
           """, nativeQuery = true)
    void deletePicturesByPostId(Long postId);
}
