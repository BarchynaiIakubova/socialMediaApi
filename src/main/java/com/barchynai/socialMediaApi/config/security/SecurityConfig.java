package com.barchynai.socialMediaApi.config.security;


import com.barchynai.socialMediaApi.repositories.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenVerifierFilter tokenVerifierFilter;

    @Bean
    AuthenticationProvider authenticationProvider(AuthInfoRepository authInfoRepository) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService((username) -> authInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " username not found"
                )));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain authorization(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/files/**").permitAll()
                        .antMatchers("/api/authentication/**").permitAll()
                        .antMatchers("/swagger", "/swagger-ui/index.html").permitAll()
                        .anyRequest()
                        .permitAll()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenVerifierFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}