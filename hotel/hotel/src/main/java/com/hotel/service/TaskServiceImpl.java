package com.hotel.service;

import com.hotel.model.entity.Task;
import com.hotel.model.enumeration.TaskStatus;
import com.hotel.repository.TaskRepository;
import com.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TaskServiceImpl {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Collection<Task> getAllNotProcessedtasks(){
        List<TaskStatus> statuses = new ArrayList<>();
        Collections.addAll(statuses, TaskStatus.CREATED,TaskStatus.UPDATED);
        return taskRepository.findAllByStatus(statuses);
    }

    @Transactional
    public void createTask(int userId, boolean isNew){
   Task task = new Task();
  // task.setUserEntity(userRepository.getOne(userId));
  task.setUserEntity(userRepository.loadUserByUserId(userId));
   task.setStatus(isNew?TaskStatus.CREATED:TaskStatus.UPDATED);
taskRepository.save(task);
    }
    @Transactional
    public void updateTask(int taskId,  TaskStatus status, String errorMessage){
       // Task taskEntity = taskRepository.getOne(taskId);
        Task task = taskRepository.loadTaskByTaskId(taskId);
        task.setStatus(status);
        task.setErrorMessage(errorMessage);
       taskRepository.save(task);

    }
}
