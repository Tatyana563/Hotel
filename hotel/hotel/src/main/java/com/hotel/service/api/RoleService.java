package com.hotel.service.api;

import com.hotel.model.dto.request.RoleRequest;
import com.hotel.model.entity.Role;

public interface RoleService {
    Role save(RoleRequest role);

    void delete(int id);
}
