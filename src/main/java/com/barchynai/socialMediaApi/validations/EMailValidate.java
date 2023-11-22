package com.barchynai.socialMediaApi.validations;


import com.barchynai.socialMediaApi.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class EMailValidate {

    public void emailValidate(String email) {

        final String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@(.+)$";

        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new BadRequestException("Unvalide email");
        }
    }
}
