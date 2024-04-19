package com.hotel.repository;

import com.hotel.model.entity.Room;
import com.hotel.model.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {

    @Query("select case when COUNT (ra) >0 then true else false end" +
            " from BookRequest ra where ra.room.id=:roomId and ra.end>=:start and ra.start<=:end")
    boolean isRoomBookedByDates(Integer roomId, Instant start, Instant end);

    @Modifying
    @Query("UPDATE Room r SET r.isDeleted = true WHERE r.hotel.id = :hotelId")
    int markRoomsAsDeleted(int hotelId);

    @Modifying
    @Query("UPDATE Room r SET r.isDeleted = true WHERE r.hotel.id = :hotelId and r.id=:roomId")
    int markSeparateRoomAsDeleted(int hotelId, int roomId);


//    @Query("select h as hotel, count(r.id) as total " +
//            "from Hotel h " +
//            "join h.roomList r " +
//            "where h.city.name = :city " +
//            "and h.starRating = :starRating " +
//            "and not exists (" +
//            "   select ra from RoomAvailability ra " +
//            "   where ra.roomId = r.id " +
//            "   and ra.end >= :start " +
//            "   and ra.start <= :end" +
//            ") " +
//            "group by h")

//    @Query("SELECT new com.hotel.model.dto.RoomAvailabilityDTO(ra.userId,ra.start,ra.end, ra.room.id,ra.room.type,ra.room.sleeps,ra.room.price)" +
//            " FROM RoomAvailability ra JOIN ra.room r WHERE ra.userId = :userId")
//    List<RoomAvailabilityDTO> findRoomAvailabilitiesByUserId(int userId);
//}

    @Query(" FROM BookRequest ra JOIN ra.room r WHERE ra.userId = :userId")
    List<BookRequest> findRoomAvailabilitiesByUserId(int userId);
}
