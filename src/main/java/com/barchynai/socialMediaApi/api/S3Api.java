package com.barchynai.socialMediaApi.api;

import com.barchynai.socialMediaApi.dto.responses.ImageResponse;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.services.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "File API", description = "S3 endpoints")
public class S3Api {

    private final S3Service service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ImageResponse upload(@RequestPart(name = "file", required = false)MultipartFile file) {

        return service.upload(file);
    }

    @PostMapping(
            path = "/all",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<ImageResponse> uploadAll(@RequestPart(name = "file") List<MultipartFile> files) {

        return service.uploadAll(files);
    }

    @DeleteMapping
    public void delete(@RequestParam String fileLink) {

        service.deletePath(fileLink);
    }

}
