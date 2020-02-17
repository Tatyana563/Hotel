package com.hotel.service;

import com.hotel.model.entity.Task;
import com.hotel.model.entity.UserEntity;
import com.hotel.model.enumeration.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collection;

public class MailScheduleService {
   private UserServiceImpl userService;
   private TaskServiceImpl taskService;
   private JavaMailSender javaMailSender;

@Autowired
   public MailScheduleService(UserServiceImpl userService, TaskServiceImpl taskService, JavaMailSender javaMailSender) {
      this.userService = userService;
      this.taskService = taskService;
      this.javaMailSender = javaMailSender;
   }
   private void sendEmailInternal(UserEntity userEntity, boolean created) throws MessagingException {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setText("Your username:" + userEntity.getName(), "Your password" + userEntity.getPassword());
      helper.setTo("nikolaev.english@gmail.com");
      helper.setSubject(created ? "New user is created" : "New user is updated");
      javaMailSender.send(mimeMessage);
   }

   @Scheduled(cron = "0/1 * * * * * ")
   public void sendEmail() throws MessagingException {
      Collection<Task> tasks = taskService.getAllNotProcessedtasks();
      if (CollectionUtils.isEmpty(tasks)) return;
      for (Task taskEntity : tasks) {
         try {
            sendEmailInternal(taskEntity.getUserEntity(), taskEntity.getStatus() == TaskStatus.CREATED);
            taskService.updateTask(taskEntity.getId(), TaskStatus.OK, null);
         } catch (Exception e) {
            taskService.updateTask(taskEntity.getId(), TaskStatus.ERROR, e.getMessage());
         }
      }
   }
   }