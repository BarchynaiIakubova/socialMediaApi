package com.barchynai.socialMediaApi.api.user;

import com.barchynai.socialMediaApi.dto.requests.authAndUser.AuthInfoRequest;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.AuthInfoRequestLogin;
import com.barchynai.socialMediaApi.dto.responses.auth.TokenResponse;
import com.barchynai.socialMediaApi.services.user.AuthInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "Auth API", description = "Auth endpoints")
public class AuthInfoApi {

    private final AuthInfoService authInfoService;

    @PostMapping("/authentication")
    TokenResponse authentication(@RequestBody AuthInfoRequestLogin authInfoRequestLogin) {
        return authInfoService.authentication(authInfoRequestLogin);
    }

}
