package com.barchynai.socialMediaApi.services;

import com.barchynai.socialMediaApi.dto.responses.ImageResponse;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final ImageService imageService;
    @org.springframework.beans.factory.annotation.Value("${cloud.aws.bucket.name}")
    private String bucketName;
    @org.springframework.beans.factory.annotation.Value("${cloud.aws.bucket.path}")
    private String bucketPath;

    public ImageResponse upload(MultipartFile file) {

        String key = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return imageService.save(key);
        } catch (IOException | S3Exception e) {

            e.printStackTrace();
            throw new BadRequestException("Failed to load the image. Please try again later");
        }

    }

//    public void deletePath(String fileLink) {
//
//    }

    public List<ImageResponse> uploadAll(List<MultipartFile> files) {

        List<ImageResponse> imageResponses = new ArrayList<>();

        for (MultipartFile file: files) {

            imageResponses.add(upload(file));
        }
        return imageResponses;
    }

    public void delete(String fileLink) {

        try {
            s3Client.deleteObject(file -> file
                    .bucket(bucketName)
                    .key(fileLink)
                    .build());

            imageService.delete(fileLink);
        } catch (S3Exception e) {

            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {

            throw new IllegalStateException(e.getMessage());
        }

    }

    public void deletePath(String fileLink) {

        try {
            String key = fileLink.substring(bucketPath.length());

            s3Client.deleteObject(file -> file
                    .bucket(bucketName)
                    .key(key)
                    .build());

            imageService.delete(fileLink);
        } catch (S3Exception e) {

            throw new IllegalStateException(e.awsErrorDetails().errorMessage());

        } catch (Exception e) {

            throw new IllegalStateException(e.getMessage());
        }

    }
}
