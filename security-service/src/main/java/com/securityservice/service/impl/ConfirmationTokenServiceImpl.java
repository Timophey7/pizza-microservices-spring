package com.securityservice.service.impl;

import com.securityservice.models.ConfirmationToken;
import com.securityservice.repository.ConfirmationTokenRepository;
import com.securityservice.repository.UserRepository;
import com.securityservice.service.ConfirmationTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public String createConfirmationToken(String email) {
        return UUID.randomUUID().toString();
    }

    @Override
    public void saveConfirmationToken(String confirmationToken, String email) {
        ConfirmationToken token = new ConfirmationToken();
        token.setToken(confirmationToken);
        token.setUserEmail(email);
        confirmationTokenRepository.save(token);
    }
}
