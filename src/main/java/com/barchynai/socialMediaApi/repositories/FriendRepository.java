package com.barchynai.socialMediaApi.repositories;

import com.barchynai.socialMediaApi.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
