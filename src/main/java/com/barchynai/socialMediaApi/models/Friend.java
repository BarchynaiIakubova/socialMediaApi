package com.barchynai.socialMediaApi.models;

import com.barchynai.socialMediaApi.enums.FriendStatus;
import com.barchynai.socialMediaApi.models.users.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_request_gen")
    @SequenceGenerator(name = "friend_request_gen", sequenceName = "friend_request_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;
}