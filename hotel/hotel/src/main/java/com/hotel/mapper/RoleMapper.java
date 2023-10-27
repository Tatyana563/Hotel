package com.hotel.mapper;

import com.hotel.model.dto.request.RoleRequest;
import com.hotel.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role roleRequestToRole(RoleRequest roleRequest);
}
