package com.hotel.controller;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.service.api.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/property")
@Validated
@RequiredArgsConstructor
public class OwnerPropertyController {

    private final HotelService hotelService;

    @GetMapping("/all_hotels_brief")
    @PreAuthorize("hasRole('OWNER')")
    public Collection<HotelBriefInfo> allHotelsBriefInfo() {
        return hotelService.listAllHotelsBriefInfo();
    }


    @PostMapping("/add")

    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<HotelDTO> create(@RequestBody @Valid HotelRequest hotelRequest, Authentication authentication) {

        return ResponseEntity.ok(hotelService.save(hotelRequest, authentication));
    }

    @DeleteMapping("/{deleteId}")
    @PreAuthorize("hasRole('OWNER') and @hotelOwnershipCheckService.check(#id,authentication)")
    public void deleteById(@PathVariable("deleteId") int id, Authentication authentication) {
        // Authentication authentication contains Principal, principal=ClaimsDto
        hotelService.delete(id, authentication);
    }
}


