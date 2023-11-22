package com.barchynai.socialMediaApi.hrlink;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_gen")
    @SequenceGenerator(name = "driver_gen", sequenceName = "driver_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String drivingExperience;
    private String birthDate;

    @ManyToMany
    @JoinTable(
            name= "car_driver",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<Car> cars;
}
