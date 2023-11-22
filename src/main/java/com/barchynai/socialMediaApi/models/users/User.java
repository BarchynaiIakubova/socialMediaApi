package com.barchynai.socialMediaApi.models.users;

import com.barchynai.socialMediaApi.enums.FriendStatus;
import com.barchynai.socialMediaApi.models.Friend;
import com.barchynai.socialMediaApi.models.Post;
import lombok.*;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;

    @OneToOne(cascade = ALL)
    private AuthInfo authInfo;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "sender")
    private List<Friend> sentRequests;

    @OneToMany(mappedBy = "receiver")
    private List<Friend> receivedRequests;
}