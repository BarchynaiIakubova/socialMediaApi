package com.barchynai.socialMediaApi.models;

import com.barchynai.socialMediaApi.models.users.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq", allocationSize = 1)
    private Long id;

    private String text;
    private String title;

    @ElementCollection

    private List<String> pictures;

    @ManyToOne
    private User user;

//    public void addPictures(String picture) {
//
//        if (this.pictures == null) {
//
//            this.pictures = new ArrayList<>();
//        }
//        this.pictures.add(picture);
//    }
}