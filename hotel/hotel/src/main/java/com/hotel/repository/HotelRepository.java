package com.hotel.repository;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Modifying
    @Query("update Hotel h set h.starRating=:stars where h.name=:hotelName ")
    void updateHotelRating(@Param("stars") StarRating starRating, @Param("hotelName") String name);
    List<Hotel> findByName(String hotelName);
    List<Hotel> findByOrderByDistanceAsc();

    @Query("select new com.hotel.model.dto.HotelBriefInfo(h.name, hc.name) from Hotel h inner join h.city hc")
    List<HotelBriefInfo> listHotelsBriefInfo();
}
