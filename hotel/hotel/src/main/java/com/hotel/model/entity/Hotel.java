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
//@Where(clause = "isDeleted is not true")// works for all JPA queries apart from links @OneToMany(hotel) and so on
public class Hotel extends OwnedEntity  implements Serializable {

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

    @ManyToOne()
    @JoinColumn(name = "CITY_FK_ID")
    private City city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> roomList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "hotel")
    private AverageCustomersFeedback averageCustomersFeedback;
}
