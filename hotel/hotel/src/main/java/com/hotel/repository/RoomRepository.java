package com.hotel.repository;

import com.hotel.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("select COUNT (ra) from RoomAvailability ra where ra.roomId=:roomId and ra.end>=:start and ra.start<=:end")
    int isRoomBookedByDates(Integer roomId, Date start, Date end);
}
