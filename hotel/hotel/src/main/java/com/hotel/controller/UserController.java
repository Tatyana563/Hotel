package com.hotel.controller;


import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/user/register")
    public User create(@ModelAttribute RegistrationRequest request) {
        return userService.register(request);
    }
}
