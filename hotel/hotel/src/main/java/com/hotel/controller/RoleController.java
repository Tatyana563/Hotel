package com.hotel.controller;

import com.hotel.model.dto.request.RoleRequest;
import com.hotel.model.entity.Role;
import com.hotel.service.api.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Validated
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/add")
    //  @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> create(@RequestBody @Valid RoleRequest roleRequest) {
        return ResponseEntity.ok(roleService.save(roleRequest));
    }

    @GetMapping("/delete/{deleteId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable("deleteId") int id) {
        roleService.delete(id);
    }
}


