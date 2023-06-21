package com.hotel.service;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomAvailability;
import com.hotel.repository.RoomAvailabilityRepository;
import com.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }


    public boolean isRoomAvailableByDates(Integer roomId, Date start, Date end) throws RoomNotFoundException {

        boolean isRoomPresentInDb = roomRepository.findById(roomId).isPresent();
        if (!isRoomPresentInDb) {
            throw new RoomNotFoundException("Room is not present in DB for id: " + roomId);
        }
        int roomBookedByDates = 0;

        roomBookedByDates = roomRepository.isRoomBookedByDates(roomId, start, end);

        return roomBookedByDates == 0;
    }

    public void saveBookedRoomToDb(Integer roomId, Date start, Date end) {
        RoomAvailability roomAvailability = new RoomAvailability();
        roomAvailability.setRoomId(roomId);
        roomAvailability.setStart(start);
        roomAvailability.setEnd(end);
        roomAvailabilityRepository.save(roomAvailability);
    }

    public void bookRoom(Integer roomId, Date start, Date end) throws RoomNotFoundException {
        boolean roomAvailableByDates = isRoomAvailableByDates(roomId, start, end);
        if (roomAvailableByDates) {
            saveBookedRoomToDb(roomId, start, end);
        }
    }
}
