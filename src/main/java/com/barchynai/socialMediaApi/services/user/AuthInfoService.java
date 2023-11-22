package com.barchynai.socialMediaApi.services.user;

import com.barchynai.socialMediaApi.config.security.JwtUtils;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.AuthInfoRequest;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.AuthInfoRequestLogin;
import com.barchynai.socialMediaApi.dto.responses.auth.TokenResponse;
import com.barchynai.socialMediaApi.models.users.AuthInfo;
import com.barchynai.socialMediaApi.validations.AuthValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthInfoService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthValidate authValidate;

    public TokenResponse authentication(AuthInfoRequestLogin authInfoRequestLogin) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authInfoRequestLogin.username(), authInfoRequestLogin.password());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        AuthInfo authInfo = authValidate.getByUsername(authenticate.getName());

        return new TokenResponse(
                jwtUtils.generateToken(authInfo),
                authInfo.getRole());
    }
}
