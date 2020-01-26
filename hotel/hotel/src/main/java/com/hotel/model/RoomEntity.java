package com.hotel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="ROOM")
public class RoomEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
    @SequenceGenerator(name="seq_city", sequenceName = "city_sequence", allocationSize = 1)
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

    @Column(name = "HOTEL_FK_ID")
    private Integer hotelId;

    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "HOTEL_FK_ID")
    private HotelEntity hotelEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(number, that.number) &&
                type == that.type &&
                sleeps == that.sleeps &&
                Objects.equals(price, that.price) &&
                Objects.equals(square, that.square) &&
                Objects.equals(parking, that.parking) &&
                Objects.equals(pets, that.pets) &&
                Objects.equals(hotelId, that.hotelId) &&
                Objects.equals(hotelEntity, that.hotelEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type, sleeps, price, square, parking, pets, hotelId, hotelEntity);
    }
}
