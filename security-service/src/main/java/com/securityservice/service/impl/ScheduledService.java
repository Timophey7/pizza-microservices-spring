package com.securityservice.service.impl;

import com.securityservice.models.ConfirmationToken;
import com.securityservice.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Scheduled(fixedRate = 150000)
    public void deleteExpiredToken(){
        List<ConfirmationToken> all = confirmationTokenRepository.findAll();
        for (ConfirmationToken confirmationToken : all) {
            if (confirmationToken.getExpiryDate().before(new Date())) {
                confirmationTokenRepository.delete(confirmationToken);
            }
        }
    }


}
