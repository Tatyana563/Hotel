package com.hotel.repository;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Modifying
    @Query("update Hotel h set h.starRating=:stars where h.name=:hotelName ")
    void updateHotelRating(@Param("stars") StarRating starRating, @Param("hotelName") String name);

    List<Hotel> findByName(String hotelName);

    List<Hotel> findByOrderByDistanceAsc();

    @Query("select new com.hotel.model.dto.HotelBriefInfo(h.id,h.name, hc.name) from Hotel h inner join h.city hc")
    List<HotelBriefInfo> listHotelsBriefInfo();

    @Query("select new com.hotel.repository.HotelCounter(r.hotel, count(r.id)) from Room r where not exists (select ra from RoomAvailability ra where ra.roomId = r.id " +
            "and ra.end>=:start and ra.start<=:end)" +
            "group by r.hotel")
    List<HotelCounter> listAvailableHotelsByDates(Date start, Date end);

    @Query("select h  from Hotel h  join fetch h.roomList as r where not exists (select ra from RoomAvailability ra where ra.roomId = r.id " +
            "and ra.end>=:start and ra.start<=:end) and h.id=:hotelId")
    Optional<Hotel> hotelWithAvailableRoomsByDates(Integer hotelId, Date start, Date end);

  //  String findNameById(Integer id);
  @Query("SELECT h.name FROM Hotel h WHERE h.id = :hotelId")
  String findHotelNameById(Integer hotelId);
}
