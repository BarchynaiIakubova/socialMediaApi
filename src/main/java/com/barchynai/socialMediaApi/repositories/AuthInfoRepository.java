package com.barchynai.socialMediaApi.repositories;

import com.barchynai.socialMediaApi.models.users.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    @Query("""
              from AuthInfo  ai
              where ai.username = :username
              """)
    Optional<AuthInfo> findByUsername(String username);

    boolean existsAuthInfoByEmail(String email);
}
