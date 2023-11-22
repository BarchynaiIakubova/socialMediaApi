package com.barchynai.socialMediaApi.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_gen"
    )
    @SequenceGenerator(
            name = "image_gen",
            sequenceName = "image_seq",
            allocationSize = 1
    )
    private Long id;

    private String link;

    public Image(String link) {
        this.link = link;
    }
}
