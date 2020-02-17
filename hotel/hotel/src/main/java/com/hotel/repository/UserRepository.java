package com.hotel.repository;

import com.hotel.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository <UserEntity, Integer> {
    @Query(" select u from UserEntity u where u.name=:p_name ")
    UserEntity loadUserByUserName(@Param("p_name") String name);

    @Query(value = "select r.name from app_role r, app_user_role ur WHERE  r.id=ur.role_id and ur.user_id=:p_userId", nativeQuery = true)
    List<String> getRoles(@Param("p_userId") Integer id);

    @Query(" select u from UserEntity u where u.id=:p_id ")
    UserEntity loadUserByUserId(@Param("p_id") Integer id);

}
