package com.hotel.repository;

import com.hotel.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("select new com.hotel.repository.HotelCounter(r.hotel, count(r.id)) from Room r where not exists (select ra from RoomAvailability ra where ra.roomId = r.id " +
            "and ra.start<=:start and ra.end>=:end)" +
            "group by r.hotel")
    List<HotelCounter> listAvailableRoomsByDates(Date start, Date end);
}
