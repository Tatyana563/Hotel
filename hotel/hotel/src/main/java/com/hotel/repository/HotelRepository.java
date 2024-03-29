package com.hotel.repository;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel> {
    @Modifying
    @Query("update Hotel h set h.starRating=:stars where h.name=:hotelName ")
    void updateHotelRating(@Param("stars") StarRating starRating, @Param("hotelName") String name);

    Optional<Hotel> findById(int id);

    List<Hotel> findByOrderByDistanceAsc();

    @Query("select new com.hotel.model.dto.HotelBriefInfo(h.id,h.name, hc.name) from Hotel h inner join h.city hc")
    List<HotelBriefInfo> listHotelsBriefInfo();

    @Query("select new com.hotel.model.dto.HotelBriefInfo(h.id,h.name, hc.name) from Hotel h inner join h.city hc where h.ownerId=:ownerId")
    List<HotelBriefInfo> listHotelsBriefInfoForOwner(int ownerId);
    @Query("select new com.hotel.repository.HotelCounter(r.hotel, count(r.id)) from Room r where not exists (select ra from RoomAvailability ra where ra.room.id = r.id " +
            "and ra.end>=:start and ra.start<=:end)" +
            "group by r.hotel")
    List<HotelCounter> listAvailableHotelsByDates(Date start, Date end);

    @Query("SELECT NEW com.hotel.repository.HotelCounter(h, COUNT(r.id)) " +
            "FROM Hotel h " +
            "JOIN h.roomList r " +
            "WHERE h.city.name = :city " +
            "AND h.starRating = :starRating " +
            "AND NOT EXISTS (" +
            "   SELECT ra FROM RoomAvailability ra " +
            "   WHERE ra.room.id = r.id " +
            "   AND ra.end >= :start " +
            "   AND ra.start <= :end" +
            ") " +
            "GROUP BY h")
    List<HotelCounter> hotelWithAvailableRoomsByDatesAccordingToCityAndStarRating(String city, StarRating starRating, Instant start, Instant end);
// todo: HQL in low case
    @Query("select h as hotel, count(r.id) as total " +
            "from Hotel h " +
            "join h.roomList r " +
            "where h.city.name = :city " +
            "and h.starRating = :starRating " +
            "and not exists (" +
            "   select ra from RoomAvailability ra " +
            "   where ra.room.id = r.id " +
            "   and ra.end >= :start " +
            "   and ra.start <= :end" +
            ") " +
            "group by h")
    List<HotelCounterProjection> hotelWithAvailableRoomsByDatesAccordingToCityAndStarRating_2(String city, StarRating starRating, Instant start, Instant end);

    //    @Query("select h  from Hotel h  join fetch h.roomList as r where not exists (select ra from RoomAvailability ra where ra.roomId = r.id " +
//            "and ra.end>=:start and ra.start<=:end) and h.id=:hotelId")
    @Query("select h  from Hotel h  join fetch h.roomList as r WHERE  function ('isRoomAvailable', r.id, :start, :end )=true   and h.id=:hotelId")
    Optional<Hotel> hotelWithAvailableRoomsByDates(Integer hotelId, Instant start, Instant end);

    //  String findNameById(Integer id);
    @Query("SELECT h.name FROM Hotel h WHERE h.id = :hotelId")
    String findHotelNameById(Integer hotelId);

    @Query("SELECT r.hotel.name  FROM Room r WHERE r.id = :roomId ")
    String findHotelNameByRoomId(Integer roomId);

    boolean existsByIdAndOwnerId(int id, int userId);

    @Modifying
    @Query("UPDATE Hotel h SET h.isDeleted = true WHERE h.id = :hotelId")
    int markHotelAsDeleted(int hotelId);

    @Query("SELECT h FROM Hotel h WHERE h.id = :hotelId AND h.isDeleted = false")
    Optional<Hotel> findByIdAndIsNotDeleted(int hotelId);
//TODO: create a few HotelDto_1, check sql query fetches not all fields
   <T> List<T> findAllBy(Class<T> type);
}
