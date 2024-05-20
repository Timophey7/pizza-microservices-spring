package com.securityservice.service.impl;

import com.securityservice.models.ConfirmationToken;
import com.securityservice.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceImplTest {

    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    ConfirmationTokenServiceImpl confirmationTokenService;

    @Test
    void saveConfirmationToken() {
        String token = "token";
        String email = "test@gmail.com";

        confirmationTokenService.saveConfirmationToken(token, email);

        verify(confirmationTokenRepository).save(any(ConfirmationToken.class));


    }
}