package com.hotel.service.shedule;

import com.hotel.model.entity.Task;
import com.hotel.model.enumeration.TaskStatus;
import com.hotel.service.MailServile;
import com.hotel.service.TaskServiceImpl;
import com.hotel.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MailScheduleService {

    private final UserServiceImpl userService;

    private final TaskServiceImpl taskService;

    private final MailServile mailServile;

    /*@Autowired
       public MailScheduleService(UserServiceImpl userService, TaskServiceImpl taskService, JavaMailSender javaMailSender) {
          this.userService = userService;
          this.taskService = taskService;
          this.javaMailSender = javaMailSender;
       }*/


   /* @Scheduled(cron = "0/1 * * * * * ")
    public void sendEmail() throws MessagingException {
        Collection<Task> tasks = taskService.getAllNotProcessedtasks();
        if (CollectionUtils.isEmpty(tasks)) return;
        for (Task taskEntity : tasks) {
            try {
                mailServile.sendLoginAndPass(taskEntity.getUserEntity(), taskEntity.getStatus() == TaskStatus.CREATED);
                taskService.updateTask(taskEntity.getId(), TaskStatus.OK, null);
            } catch (Exception e) {
                taskService.updateTask(taskEntity.getId(), TaskStatus.ERROR, e.getMessage());
            }
        }
    }*/
}