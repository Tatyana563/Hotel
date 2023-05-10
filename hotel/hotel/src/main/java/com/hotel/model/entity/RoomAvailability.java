package com.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="roomAvailabily")
public class RoomAvailability {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer roomId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date start;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date")
    private Date end;
}
