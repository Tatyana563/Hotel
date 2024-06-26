package com.hotel.service.impl;

import com.hotel.events.model.RoomBookedEvent;
import com.hotel.exception_handler.exception.HotelNotFoundException;
import com.hotel.exception_handler.exception.RoomNotFoundException;
import com.hotel.mapper.RoomAvailabilityMapper;
import com.hotel.mapper.RoomMapper;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.RoomAvailabilityDTO;
import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.request.RoomRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.dto.response.RequestStatus;
import com.hotel.model.entity.BookRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Room;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.BookRequestRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.UserRepository;
import com.hotel.repository.specifications.RoomSpecification;
import com.hotel.service.api.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final ApplicationEventPublisher publisher;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookRequestRepository roomAvailabilityRepository;
    private final RoomMapper roomMapper;

    private final RoomAvailabilityMapper roomAvailabilityMapper;

    @Override
    @Transactional
    public void deleteSeparateRoom(int hotelId, int roomId) {
        roomRepository.markSeparateRoomAsDeleted(hotelId, roomId);
    }

    @Override
    public RoomDTOWithHotelDTO save(int hotelId, RoomRequest roomRequest) {

        Room room = roomMapper.roomRequestToRoom(roomRequest);
        Optional<Hotel> hotel = hotelRepository.findByIdAndIsNotDeleted(hotelId);
        if (hotel.isPresent()) {
            room.setHotel(hotel.get());
            Room savedRoom = roomRepository.save(room);
            return roomMapper.roomToRoomDTOWithHotelDTO(savedRoom);
        } else throw new HotelNotFoundException(hotelId);
    }

    public boolean isRoomAvailableByDates(Integer roomId, Instant start, Instant end) throws RoomNotFoundException {

        boolean isRoomPresentInDb = roomRepository.existsById(roomId);
        if (!isRoomPresentInDb) {
            throw new RoomNotFoundException(roomId);
        }

        return !roomRepository.isRoomBookedByDates(roomId, start, end);

    }

    public BookRequest saveBookRequest(Integer roomId, Instant start, Instant end, Integer userId) {
        BookRequest bookRequest = new BookRequest();
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        bookRequest.setRoom(room);
        bookRequest.setStart(start);
        bookRequest.setEnd(end);
        bookRequest.setUserId(userId);
        return roomAvailabilityRepository.save(bookRequest);
    }

    @Transactional
    @Override
    public BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException {
        boolean roomAvailableByDates = isRoomAvailableByDates(roomId, bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
        String hotelName = hotelRepository.findHotelNameByRoomId(roomId);
        if (roomAvailableByDates) {
            int userIdByEmail = userRepository.findUserIdByEmail(bookingRequest.getEmail());
            saveBookRequest(roomId, bookingRequest.getCheckIn(), bookingRequest.getCheckOut(), userIdByEmail);
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
//TODO: write mapper
    @Override
    public   List<RoomAvailabilityDTO> findRoomsBookedByMe(int userId) {
        List<BookRequest> roomAvailabilities = roomRepository.findRoomAvailabilitiesByUserId(userId);
       return roomAvailabilities.stream().map(roomAvailabilityMapper::roomAvailabilityToRoomAvailabilityDTO).collect(Collectors.toList());

    }
}
