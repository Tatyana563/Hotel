package com.hotel.service.auditable;

import com.hotel.model.dto.ClaimsDto;
import com.hotel.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.Optional;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditService {
    private final PlatformTransactionManager platformTransactionManager;
    private final AuditLogRepository auditLogRepository;

    @Around("@annotation(annotation)")
    public Object hangAround(ProceedingJoinPoint proceedingJoinPoint, AuditAnnotation annotation) throws Throwable {
        Object returnedValue = null;
        Exception caughtException = null;
        AuditEvent auditEvent = new AuditEvent();
        Instant now = Instant.now();
        long before = now.toEpochMilli();
        auditEvent.setCreationDate(now);
        try {
            returnedValue = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            auditEvent.setSuccess(false);
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            auditEvent.setStackTrace(stringWriter.toString());
            auditEvent.setMessage(e.getMessage());
            caughtException = e;
        }
        long after = System.currentTimeMillis();

        auditEvent.setDuration(after - before);
        auditEvent.setOperation(annotation.operation());
        ClaimsDto principal = (ClaimsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional.of(principal).map(ClaimsDto::getId).ifPresent(auditEvent::setModifiedBy);
        // auditEvent.setModifiedBy(Optional.of(principal).map(ClaimsDto::getId).orElse(null));
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
        auditLogRepository.save(auditEvent);
        platformTransactionManager.commit(status);
        if (caughtException != null) {
            throw caughtException;
        }
        return returnedValue;

    }
}
