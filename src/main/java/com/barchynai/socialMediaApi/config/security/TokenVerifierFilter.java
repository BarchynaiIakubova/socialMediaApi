package com.barchynai.socialMediaApi.config.security;

import com.barchynai.socialMediaApi.models.users.AuthInfo;
import com.barchynai.socialMediaApi.validations.AuthValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenVerifierFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthValidate authValidate;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);

        try {
            jwtUtils.validateToken(token);
        } catch (Exception e) {
            response.sendError(403, e.getMessage());
            return;
        }
        String emailFromJwt = jwtUtils.getEmailFromJwt(token);
        AuthInfo authInfo = authValidate.getByUsername(emailFromJwt);

        assert authInfo != null;
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        authInfo.getUsername(),
                        null,
                        authInfo.getAuthorities()
                ));
            filterChain.doFilter(request, response);
    }
}
