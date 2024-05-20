package com.securityservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    MimeMessageHelper mimeMessageHelper;

    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    void sendEmail() throws MessagingException {
        String to = "test@test.com";
        String body = "test body";
        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(mailSender).send(mimeMessage);

        emailService.sendEmail(to, body);

        verify(mailSender).send(mimeMessage);
        verify(mimeMessageHelper).setTo(to);
        verify(mimeMessageHelper).setSubject("pizza");
        verify(mimeMessage).setFrom("pizza@gmail.com");
        verify(mimeMessageHelper).setText("вот ваш код для подтверждения почты ");


    }
}