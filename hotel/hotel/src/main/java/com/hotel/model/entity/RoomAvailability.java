package com.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "room_availability")
public class RoomAvailability extends BaseEntity implements Serializable {
    @JsonIgnore

    private Integer roomId;
    private Integer userId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Instant start;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Instant end;
}
