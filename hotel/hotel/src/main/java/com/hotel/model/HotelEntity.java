package com.hotel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="HOTEL")

public class HotelEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotel")
    @SequenceGenerator(name="seq_hotel", sequenceName = "hotel_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private StarRating starRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEALS", nullable = false)
    private Meals meals;

    @Column(name = "DISTANCE")
    private Integer distance;

    @Column(name = "CITY_FK_ID")
    private Integer cityId;

    @ManyToOne()
    @JoinColumn(name = "CITY_FK_ID")
    private CityEntity cityEntity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotelEntity")
    private List<CityEntity> roomList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelEntity that = (HotelEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                starRating == that.starRating &&
                meals == that.meals &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(cityEntity, that.cityEntity) &&
                Objects.equals(roomList, that.roomList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, starRating, meals, distance, cityId, cityEntity, roomList);
    }
}
