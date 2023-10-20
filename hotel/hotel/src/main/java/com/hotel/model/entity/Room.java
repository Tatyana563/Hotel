package com.hotel.model.entity;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
    @SequenceGenerator(name = "seq_city", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;
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

    @Column(name = "HOTEL_FK_ID", insertable = false, updatable = false)
    private Integer hotelId;

    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "HOTEL_FK_ID")
    private Hotel hotel;

}
