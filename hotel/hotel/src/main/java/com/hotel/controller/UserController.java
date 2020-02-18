package com.hotel.controller;


import com.hotel.model.entity.UserEntity;
import com.hotel.service.UserService;
import com.hotel.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Stream;

@Controller
public class UserController {
    @Autowired
    public static String pass;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value ="/user/new", method = RequestMethod.POST)
    public String create(
            @RequestParam("login") String name,
            @RequestParam("password") String password,
            @RequestParam("phonenumber") String phone,
            @RequestParam("email") String email
    ){
        final UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        pass = userEntity.getPassword();
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        userService.save(userEntity);
        return "/login";
    }
}
