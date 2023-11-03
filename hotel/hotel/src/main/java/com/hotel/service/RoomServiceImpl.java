package com.hotel.service;

import com.hotel.events.model.RoomBookedEvent;
import com.hotel.exception_handler.exception.RoomNotFoundException;
import com.hotel.mapper.RoomMapper;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.dto.response.RequestStatus;
import com.hotel.model.entity.Room;
import com.hotel.model.entity.RoomAvailability;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.RoomAvailabilityRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.specifications.RoomSpecification;
import com.hotel.service.api.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final ApplicationEventPublisher publisher;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;
    private final RoomMapper roomMapper;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    public boolean isRoomAvailableByDates(Integer roomId, Date start, Date end) throws RoomNotFoundException {

        boolean isRoomPresentInDb = roomRepository.existsById(roomId);
        if (!isRoomPresentInDb) {
            throw new RoomNotFoundException(roomId);
        }


        return !roomRepository.isRoomBookedByDates(roomId, start, end);

    }

    public RoomAvailability saveBookRequest(Integer roomId, Date start, Date end) {
        RoomAvailability roomAvailability = new RoomAvailability();
        roomAvailability.setRoomId(roomId);
        roomAvailability.setStart(start);
        roomAvailability.setEnd(end);
        return roomAvailabilityRepository.save(roomAvailability);
    }

    @Transactional
    @Override
    public BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException {
        boolean roomAvailableByDates = isRoomAvailableByDates(roomId, bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
        String hotelName = hotelRepository.findHotelNameByRoomId(roomId);
        if (roomAvailableByDates) {
            saveBookRequest(roomId, bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//                @Override
//                public void afterCommit() {
//                   bookingEmailService.sendEmail(start, end,username,email,hotelName);
//                }
//            });
            publisher.publishEvent(new RoomBookedEvent(bookingRequest, hotelName));
            return new BookingResponse(bookingRequest.getCheckIn(), bookingRequest.getCheckOut(), hotelName, roomId, RequestStatus.SUCCESSFULLY_BOOKED);
        }
        return new BookingResponse(bookingRequest.getCheckIn(), bookingRequest.getCheckOut(), hotelName, roomId, RequestStatus.ALREADY_BOOKED);
    }

    @Override
    public List<RoomDTO> findRoomsWithFilters(FilterDTO filters) {

        Specification<Room> spec = new RoomSpecification(filters);
        List<Room> rooms = roomRepository.findAll(spec);

        return rooms.stream().map(roomMapper::roomToRoomDTO).collect(Collectors.toList());

    }
}
