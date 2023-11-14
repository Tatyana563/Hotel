package com.hotel.repository;

import com.hotel.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {

    @Query("select case when COUNT (ra) >0 then true else false end" +
            " from RoomAvailability ra where ra.roomId=:roomId and ra.end>=:start and ra.start<=:end")
    boolean isRoomBookedByDates(Integer roomId, Date start, Date end);

    @Modifying
    @Query("UPDATE Room r SET r.isDeleted = true WHERE r.hotel.id = :hotelId")
    int markRoomsAsDeleted(int hotelId);
}
