package com.securityservice.service;

public interface ConfirmationTokenService {

    String createConfirmationToken(String email);

    void saveConfirmationToken(String confirmationToken, String email);

}
