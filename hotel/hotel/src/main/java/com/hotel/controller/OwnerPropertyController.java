package com.hotel.controller;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.service.api.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    // TODO: move into service, use mapper!!!!
   @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<HotelDTO> create(@RequestBody @Valid HotelRequest hotelRequest,Authentication authentication) {

        //TODO: return hotelDTO
        return ResponseEntity.ok(hotelService.save(hotelRequest, authentication));
    }

    @GetMapping("/delete/{deleteId}")
    @PreAuthorize("hasRole('OWNER') and @ownershipCheck.check(#id,authentication)")
    public void deleteById(@PathVariable("deleteId") int id, Authentication authentication) {
        // Authentication authentication contains Principal, principal=ClaimsDto
        hotelService.delete(id, authentication);
    }
}


