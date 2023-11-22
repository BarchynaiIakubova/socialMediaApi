package com.barchynai.socialMediaApi.repositories;

import com.barchynai.socialMediaApi.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i where i.link = :link")
    Optional<Image> findByLink(String link);
}
