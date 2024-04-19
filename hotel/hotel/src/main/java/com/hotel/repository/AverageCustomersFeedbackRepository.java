package com.hotel.repository;

import com.hotel.model.entity.AverageCustomersFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AverageCustomersFeedbackRepository extends JpaRepository<AverageCustomersFeedback, Integer> {

//    @Query("select case when COUNT (ra) >0 then true else false end" +
//            " from AverageHotelRating ra where ra.hotel.id=:hotelId ")
    Optional<AverageCustomersFeedback> findByHotelId(Integer hotelId);
}
