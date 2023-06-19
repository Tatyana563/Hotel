package com.hotel.model.entity;

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
@Table(name = "city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
    @SequenceGenerator(name = "seq_city", sequenceName = "city_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Hotel> hotelList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City that = (City) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) &&
                Objects.equals(hotelList, that.hotelList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, hotelList);
    }
}
