package com.hotel.controller;

import com.hotel.model.dto.*;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.dto.request.RoomRequest;
import com.hotel.model.dto.request.SearchRequest;
import com.hotel.model.dto.request.TestInterval;
import com.hotel.repository.specifications.HotelSpecification;
import com.hotel.service.api.HotelService;
import com.hotel.service.api.RoomService;
import com.hotel.validation.date.ValidCustomInterval;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/hotels")
//@Validated
@RequiredArgsConstructor
@Slf4j
public class OwnerPropertyController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping("/all_hotels_brief")
    @PreAuthorize("hasRole('OWNER')")
    public Collection<HotelBriefInfo> allHotelsBriefInfo(Authentication authentication) {
        ClaimsDto claimsDto = (ClaimsDto) authentication.getPrincipal();
        int userId = claimsDto.getId();

        HotelSpecification hotelSpecification = HotelSpecification.builder()
                .ownerId(userId)
                .build();

        return hotelService.listAllHotelsBriefInfo(hotelSpecification);
    }
    @GetMapping("/all_hotels_list")
    @PreAuthorize("hasRole('OWNER')")
    public Collection<HotelDTOWithoutCity> allHotelsList() {
        return hotelService.findAll(HotelDTOWithoutCity.class);
    }


    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody @Valid HotelRequest hotelRequest, Authentication authentication) {

        return ResponseEntity.ok(hotelService.save(hotelRequest, authentication));
    }

    @PostMapping("/test")
    public void test(@RequestBody @Valid  TestInterval interval) {
        log.info("testing validator");
    }

    @PostMapping("/{hotelId}/room")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.checkOwnership(#hotelId,authentication)")
    public ResponseEntity<RoomDTOWithHotelDTO> createRoom(@PathVariable int hotelId, @RequestBody @Valid RoomRequest roomRequest, Authentication authentication) {

        return ResponseEntity.ok(roomService.save(hotelId, roomRequest));
    }
    @DeleteMapping("/{hotelId}/room/{deleteId}")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.checkOwnership(#hotelId,authentication)")
    public void deleteRoom(@PathVariable int hotelId, @PathVariable int deleteId, Authentication authentication) {
     roomService.deleteSeparateRoom(hotelId, deleteId);
    }

    @DeleteMapping("/{deleteId}")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.checkOwnership(#id,authentication)")
    public void deleteById(@PathVariable("deleteId") int id, Authentication authentication) {
        // Authentication authentication contains Principal, principal=ClaimsDto
        hotelService.delete(id);
    }
}


