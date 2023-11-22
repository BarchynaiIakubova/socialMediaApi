package com.barchynai.socialMediaApi.config.security;

import com.barchynai.socialMediaApi.exceptions.WrongPasswordException;
import com.barchynai.socialMediaApi.models.users.AuthInfo;
import com.barchynai.socialMediaApi.repositories.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AuthInfo authInfo = authInfoRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), authInfo.getPassword())) {

            throw new WrongPasswordException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(authInfo.getUsername(), null, authInfo.getAuthorities());
    }
}
