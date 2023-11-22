package com.barchynai.socialMediaApi.config.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Getter
@Setter
public class S3Config {

    @Value("${cloud.aws.bucket.secret-key}")
    private String AWS_SECRET_KEY;
    @Value("${cloud.aws.bucket.access-key}")
    private String AWS_ACCESS_KEY;
    @Value("${cloud.aws.bucket.region}")
    private String REGION;

    @Bean
    S3Client s3Client() {
        Region region = Region.of(REGION);

        final AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(
                AWS_ACCESS_KEY, AWS_SECRET_KEY
        );

        return S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

    @Bean
    Route53Client route53Client() {
        AwsCredentialsProvider acp = StaticCredentialsProvider.create(AwsBasicCredentials.create(AWS_ACCESS_KEY, AWS_SECRET_KEY));
        return Route53Client.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(acp).build();
    }
}
