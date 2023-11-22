package com.barchynai.socialMediaApi.services;

import com.barchynai.socialMediaApi.dto.responses.ImageResponse;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.models.Image;
import com.barchynai.socialMediaApi.repositories.ImageRepository;
import com.barchynai.socialMediaApi.validations.ImageValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final ImageValidate imageValidate;

    @Value("${cloud.aws.bucket.path}")
    private String path;

    public ImageResponse save(String link) {

        imageRepository.save(new Image(link));

        return new ImageResponse(path + link);
    }

    public Response delete(String link) {

        imageRepository.delete(imageValidate.findByImageByLink(link));

        return new Response("Image removed");
    }

    public String getImage(String image) {

        if (Objects.nonNull(image)) {

            return image.substring(path.length());
        }
        return null;
    }

}
