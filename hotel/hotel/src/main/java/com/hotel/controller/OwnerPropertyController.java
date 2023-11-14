package com.hotel.controller;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.dto.request.RoomRequest;
import com.hotel.service.api.HotelService;
import com.hotel.service.api.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/hotels")
@Validated
@RequiredArgsConstructor
public class OwnerPropertyController {

    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping("/all_hotels_brief")
    @PreAuthorize("hasRole('OWNER')")
    public Collection<HotelBriefInfo> allHotelsBriefInfo() {
        return hotelService.listAllHotelsBriefInfo();
    }


    @PostMapping

    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody @Valid HotelRequest hotelRequest, Authentication authentication) {

        return ResponseEntity.ok(hotelService.save(hotelRequest, authentication));
    }

    @PostMapping("/{hotelId}/room")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.check(#hotelId,authentication)")
    public ResponseEntity<RoomDTOWithHotelDTO> createRoom(@PathVariable int hotelId, @RequestBody @Valid RoomRequest roomRequest, Authentication authentication) {

        return ResponseEntity.ok(roomService.save(hotelId, roomRequest));
    }


    @DeleteMapping("/{deleteId}")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.check(#id,authentication)")
    public void deleteById(@PathVariable("deleteId") int id, Authentication authentication) {
        // Authentication authentication contains Principal, principal=ClaimsDto
        hotelService.delete(id);
    }
}


