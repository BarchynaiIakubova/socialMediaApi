package com.barchynai.socialMediaApi.validations;

import com.barchynai.socialMediaApi.models.Image;
import com.barchynai.socialMediaApi.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class ImageValidate {

    private final ImageRepository imageRepository;

    public Image findById(Long imageId) {

        return imageRepository.findById(imageId).orElseThrow(
                () -> new NotFoundException("Image not found")
        );
    }

    public Image findByImageByLink(String link) {

        return imageRepository.findByLink(link).orElseThrow(
                () -> new NotFoundException("Image not found")
        );
    }
}
