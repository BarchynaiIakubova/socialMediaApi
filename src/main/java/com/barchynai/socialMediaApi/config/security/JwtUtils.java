package com.barchynai.socialMediaApi.config.security;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Component
public class JwtUtils {

    @Value("${app.jwt.expired-at}")
    private long expireddAt;
    @Value("${app.jwt.secret-word}")
    private String secretWord;

    Date expirationDate = Date.from(ZonedDateTime.now().plusDays(60).toInstant());

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretWord)
                .compact();
    }

    public void validateToken(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(jwt);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT is expired", e);
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Unsupported JWT");
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new MalformedJwtException("Malformed JWT");
        } catch (SignatureException e) {
            throw new SignatureException("Signature Exception (JWT) !");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal Argument (JWT)");
        }
    }

    public String getEmailFromJwt(String jwt) {

        return Jwts.parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
