package com.hotel.repository;

import com.hotel.model.HotelEntity;
import com.hotel.model.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Integer>, JpaSpecificationExecutor<HotelEntity> {
@Modifying
    @Query("update HotelEntity h set h.starRating=:stars where h.name=:hotelName ")
    void updateHotelRating(@Param("starRating") StarRating starRating, @Param("hotelName") String name);
}
