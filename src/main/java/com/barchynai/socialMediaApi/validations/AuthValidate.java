package com.barchynai.socialMediaApi.validations;

import com.barchynai.socialMediaApi.exceptions.BadRequestException;
import com.barchynai.socialMediaApi.models.users.AuthInfo;
import com.barchynai.socialMediaApi.repositories.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class AuthValidate {

    private final AuthInfoRepository authInfoRepository;

    public void existsEMail(String email) {
        if (authInfoRepository.existsAuthInfoByEmail(email)) {

            throw new BadRequestException("This email is already in use");
        }
    }

    public AuthInfo getByUsername(String username) {
        return authInfoRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }
}
