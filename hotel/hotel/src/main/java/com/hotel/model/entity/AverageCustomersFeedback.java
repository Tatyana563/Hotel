package com.hotel.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "average_customers_feedback")
public class AverageCustomersFeedback extends DeletableEntity implements Serializable {

    private Double averageMark;
    private int feedbacksNumber;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hotel_fk_id", referencedColumnName = "id")
    private Hotel hotel;
}



