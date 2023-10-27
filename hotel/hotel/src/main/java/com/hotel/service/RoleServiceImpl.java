package com.hotel.service;

import com.hotel.exception_handler.RoleNotFoundException;
import com.hotel.mapper.RoleMapper;
import com.hotel.model.dto.request.RoleRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Role;
import com.hotel.repository.RoleRepository;
import com.hotel.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public Role save(RoleRequest roleRequest) {
        Role role = roleMapper.roleRequestToRole(roleRequest);
        return roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        Optional<Role> role = roleRepository.findById(id);
        role.ifPresentOrElse(
                roleRepository::delete,
                () -> {
                    throw new RoleNotFoundException("Role with id " + id + " not found");
                }
        );
    }
}
