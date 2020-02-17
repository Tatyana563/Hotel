package com.hotel.repository;

import com.hotel.model.entity.Task;
import com.hotel.model.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    @Query("select t from Task t where status in(:statuses)")
    Collection<Task> findAllByStatus(@Param("statuses") Collection<TaskStatus> status);

    @Query(" select t from Task t where t.id=:p_id ")
    Task loadTaskByTaskId(@Param("p_id") Integer id);
}
