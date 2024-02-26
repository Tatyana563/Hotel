package com.hotel.model.entity;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room extends DeletableEntity implements Serializable {

    @Column(name = "NUMBER", nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private RoomType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "SLEEPS", nullable = false)
    private Sleeps sleeps;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "SQUARE")
    private Double square;

    @Column(name = "PARKING")
    private Boolean parking;

    @Column(name = "ANIMALS")
    private Boolean pets;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "HOTEL_FK_ID")
    @ToString.Exclude
    private Hotel hotel;

}
