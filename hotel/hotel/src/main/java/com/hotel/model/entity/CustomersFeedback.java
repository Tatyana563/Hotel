package com.hotel.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers_feedback")
public class CustomersFeedback extends DeletableEntity implements Serializable {
    private String comment;
    private int location;
    private int priceQuality;
    private int cleanliness;
    private Double mark;
    private Instant date;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "user_fk_id", referencedColumnName = "id")
//    private User user;
//
//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "hotel_fk_id", referencedColumnName = "id")
//    private Hotel hotel;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_request_fk_id", referencedColumnName = "id",nullable = false)
    private BookRequest bookRequest;
}

//The user can leave  a comment to the hotel only after booking  a room not later that 30 days after booking end date;
//The user can leave as many comments as many rooms he/she books;


