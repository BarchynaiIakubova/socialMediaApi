package com.barchynai.socialMediaApi.repositories;


import com.barchynai.socialMediaApi.dto.responses.user.UserResponseForGetAll;
import com.barchynai.socialMediaApi.models.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
           select new com.barchynai.socialMediaApi.dto.responses.user.UserResponseForGetAll(
           u.id,
           concat(u.firstName, ' ', u.lastName),
           case when u.avatar is null then :defaultAvatar
           else concat(:bucketPath, u.avatar) end)
           from User u
           where ((:search is null )
                   or (upper(u.firstName) like upper(concat('%', :search, '%'))))
           """)
    List<UserResponseForGetAll> findAllUsers(String bucketPath, String defaultAvatar, String search, Pageable pageable);


    @Query("""
           from User u
           inner join AuthInfo ai on u.authInfo.id = ai.id
           where ai.username = :name
           """)
    Optional<User> findIdByUsername(String name);


}
