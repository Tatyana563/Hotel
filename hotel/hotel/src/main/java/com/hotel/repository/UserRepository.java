package com.hotel.repository;

import com.hotel.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(" select u from User u where u.name=:p_name ")
    User loadUserByUserName(@Param("p_name") String name);

    @Query(value = "select r.name from app_role r, app_user_role ur WHERE  r.id=ur.role_id and ur.user_id=:p_userId", nativeQuery = true)
    List<String> getRoles(@Param("p_userId") Integer id);

    @Query(" select u from User u where u.id=:p_id ")
    User loadUserByUserId(@Param("p_id") Integer id);

    Optional<User> findUserByEmail(String email);

    User findByTokenReset(String token);

    boolean existsByEmail(String email);

    @Query(" select u.enabled from User u where u.email=:email")
    boolean isEnabled(String email);

    @Query(" select u.id from User u where u.email=:email")
    int findUserIdByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);

}
