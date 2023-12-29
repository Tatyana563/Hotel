package com.hotel.service.auditable;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "audit")
public class AuditEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Operation operation;
    private Integer modifiedBy;
    private Instant creationDate;
    private Long duration;
    private boolean success;
    private String stackTrace;
    private String message;

}