package com.hotel.service.cron;

import com.hotel.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenCleanupService {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void cleanExpiredTokens() {
        Date currentDate = new Date();
        tokenRepository.deleteByExpiryDateBefore(currentDate);
    }
}
