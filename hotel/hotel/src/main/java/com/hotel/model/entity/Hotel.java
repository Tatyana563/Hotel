package com.hotel.model.entity;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.PostgreSQLEnumType;
import com.hotel.model.enumeration.StarRating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="hotel")
@TypeDef(
        name = "hotel_meals",
        typeClass = PostgreSQLEnumType.class
)
@TypeDef(
        name = "hotel_stars",
        typeClass = PostgreSQLEnumType.class
)
public class Hotel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hotel")
    @SequenceGenerator(name="seq_hotel", sequenceName = "hotel_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Type(type="hotel_stars")
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private StarRating starRating;

@Type(type="hotel_meals")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel that = (Hotel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                starRating == that.starRating &&
                meals == that.meals &&
                Objects.equals(distance, that.distance) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(city, that.city) &&
                Objects.equals(roomList, that.roomList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, starRating, meals, distance, cityId, city, roomList);
    }
}
