package com.hotel.repository;

import com.hotel.model.entity.User;
import com.hotel.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, UUID> {

   void deleteByExpiryDateBefore(Date currenDate);

   VerificationToken findByUserEmail(String email);
}
