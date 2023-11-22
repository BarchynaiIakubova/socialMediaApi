package com.barchynai.socialMediaApi.validations;

import com.barchynai.socialMediaApi.models.users.User;
import com.barchynai.socialMediaApi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class UserValidate {

    private final UserRepository userRepository;

    public User findById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User is not found"));
    }

    public User getByAuthentication() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findIdByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User is not found"));
    }


}
