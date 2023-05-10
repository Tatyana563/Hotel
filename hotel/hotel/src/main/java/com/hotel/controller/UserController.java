//package com.hotel.controller;
//
//
//import com.hotel.model.entity.UserEntity;
//import com.hotel.service.MailServile;
//import com.hotel.service.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class UserController {
//
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    private MailServile mailServile;
//
//    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
//    public String create(
//            @RequestParam("login") String name,
//            @RequestParam("password") String password,
//            @RequestParam("phonenumber") String phone,
//            @RequestParam("email") String email
//    ) {
//        final UserEntity userEntity = new UserEntity();
//        userEntity.setName(name);
//       //String pass = userEntity.getPassword();
//        userEntity.setEmail(email);
//        userEntity.setPhone(phone);
//        userEntity.setPassword(passwordEncoder.encode(password));
//        userService.save(userEntity);
//        mailServile.sendLoginAndPass(name, password, true);
//        return "/login";
//    }
//}
