//package com.hotel.service;
//
//import com.hotel.model.entity.UserEntity;
//import com.hotel.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//@Service
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity user = userRepository.loadUserByUserName(username);
//        if(user==null){
//            throw new UsernameNotFoundException("User" + username +"not found");
//        }
//        List<String> roles = userRepository.getRoles(user.getId());
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(roles)) {
//            for (String role : roles) {
//                grantedAuthorities.add(new SimpleGrantedAuthority(role));
//            }
//        }
//        return new User(user.getName(), user.getPassword(), grantedAuthorities);
//    }
//    }
//
