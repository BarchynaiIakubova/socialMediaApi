package com.barchynai.socialMediaApi.validations;

import com.barchynai.socialMediaApi.exceptions.BadRequestException;
import com.barchynai.socialMediaApi.models.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordValidate {

    private final PasswordEncoder passwordEncoder;

    public void isValid(String password) {

        if (password.length() < 6) {

            throw new BadRequestException("Weak password");
        }
    }

    public void validatePasswordIsEmpty(String oldPassword) {

        if (oldPassword.isBlank()) {

            throw new BadRequestException("Old password should not be empty");
        }
    }

    public void validatePasswordIsNotSame(String newPassword, String oldPassword) {

        if (newPassword.equals(oldPassword)) {

            throw new BadRequestException("The old and new password must not match");
        }
    }

    public void validatePasswordIsCorrect(String oldPassword, String newPassword, User user) {

        if (!newPassword.isBlank()) {

            if (passwordEncoder.matches(oldPassword, user.getAuthInfo().getPassword())) {

                validatePasswordIsEmpty(oldPassword);

                isValid(newPassword);

                validatePasswordIsNotSame(oldPassword, newPassword);

                user.getAuthInfo().setPassword(passwordEncoder.encode(newPassword));
            } else {
                throw new BadRequestException("Invalid old password");
            }
        }
    }
}
