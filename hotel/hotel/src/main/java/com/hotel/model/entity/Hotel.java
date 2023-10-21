package com.hotel.model.entity;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private StarRating starRating;

    @Column(name = "MEALS")
    @Enumerated(EnumType.STRING)
    private Meals meals;

    @Column(name = "DISTANCE")
    private Integer distance;

    @Column(name = "CITY_FK_ID")
    private Integer cityId;

    @ManyToOne()
    @JoinColumn(name = "CITY_FK_ID", insertable = false, updatable = false)
    private City city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> roomList = new ArrayList<>();
}
