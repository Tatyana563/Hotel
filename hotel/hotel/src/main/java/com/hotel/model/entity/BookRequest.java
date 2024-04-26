package com.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "book_request")
public class BookRequest extends BaseEntity implements Serializable {
    @JsonIgnore
    private Integer userId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Instant start;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Instant end;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ROOM_ID")
    @ToString.Exclude
    private Room room;
// TODO: relation owner CustomerFeedback as it has field "bookRequest" which is in  mappedBy.
  //  First save CustomerFeedback
    // if i create BookRequest with CustomersFeedback and save, in DB only BookRequest
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "bookRequest")
    private CustomersFeedback feedback;
}

