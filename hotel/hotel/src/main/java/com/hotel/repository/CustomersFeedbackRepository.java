package com.hotel.repository;

import com.hotel.model.dto.AverageHotelRatingDto;
import com.hotel.model.entity.CustomersFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersFeedbackRepository extends JpaRepository<CustomersFeedback, Integer> {
    //TODO: 1)select avg(mark), count join romm_availability, room and hotel to get hotel_id
  //  2) insert into average_customers_feedback in 1 request

    @Query("SELECT new com.hotel.model.dto.AverageHotelRatingDto(y.bookRequest.room.hotel.id, AVG(y.mark)) FROM CustomersFeedback y GROUP BY y.bookRequest.room.hotel.id")
    List<AverageHotelRatingDto> calculateAverageMarkGroupById();
// working:
//    @Query(value="SELECT CASE WHEN COUNT(ra) > 0 THEN true ELSE false END " +
//            "FROM room_availability ra WHERE ra.user_id = :userId " +
//            "AND ra.end_date <= :commentDate AND DATE_PART('day',  CAST(:commentDate as timestamp) - ra.end_date) <= 30", nativeQuery = true)
//    boolean isCommentDateNotMoreThan30DaysFromCheckOut(Integer userId, Instant commentDate);

    @Query("SELECT CASE WHEN COUNT(ra) > 0 THEN true ELSE false END " +
            "FROM BookRequest ra WHERE ra.userId = :userId " +
            "AND ra.end>= :maxCheckoutDate")
    boolean validateDateDifferenceFromCheckOut(Integer userId, Instant maxCheckoutDate);


    @Query("FROM CustomersFeedback c WHERE c.bookRequest.id = :id ")
    Optional<CustomersFeedback> findByBookRequestId(Integer id);








}
