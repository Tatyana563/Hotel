package com.hotel.repository;

import com.hotel.service.auditable.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuditLogRepository extends JpaRepository<AuditEvent, Long> {
}
