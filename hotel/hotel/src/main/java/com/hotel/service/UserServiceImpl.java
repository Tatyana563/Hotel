package com.hotel.service;

import com.hotel.exception_handler.UserAlreadyCreated;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public User register(RegistrationRequest request) {
        User userByLogin = userRepository.findUserByEmail(request.getEmail());
        User savedUser = null;
        if (userByLogin == null) {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setLogin(request.getLogin());
            user.setSurname(request.getSurname());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());

            savedUser = userRepository.save(user);
            return savedUser;
        } else throw new UserAlreadyCreated(request.getEmail());
    }
}
