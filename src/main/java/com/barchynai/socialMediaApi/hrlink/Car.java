package com.barchynai.socialMediaApi.hrlink;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_gen")
    @SequenceGenerator(name = "car_gen", sequenceName = "car_seq", allocationSize = 1)
    private Long id;
    private String brand;
    private String manufacturingYear;
    private Double mileage;

    @ManyToMany(mappedBy = "cars")
    private List<Driver> drivers;
}
