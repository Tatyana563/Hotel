package com.hotel.model.entity;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hotel")
//@Where(clause = "isDeleted is not true")// works for all JPA queries apart from links @OneToMany(hotel) and so on
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

    @Column(name = "USER_FK_ID")
    private Integer userId;

    @ManyToOne()
    @JoinColumn(name = "CITY_FK_ID")
    private City city;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Room> roomList = new ArrayList<>();
}
