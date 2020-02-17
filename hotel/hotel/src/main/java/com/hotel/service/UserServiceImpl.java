package com.hotel.service;

import com.hotel.model.entity.UserEntity;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private TaskServiceImpl taskService;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(TaskServiceImpl taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void save(UserEntity userEntity) {
        final boolean isNew = userEntity.getId() == null;
        userRepository.save(userEntity);
        taskService.createTask(userEntity.getId(), isNew);

    }
}
