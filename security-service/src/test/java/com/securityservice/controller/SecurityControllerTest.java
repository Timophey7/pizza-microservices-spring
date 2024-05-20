package com.securityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityservice.models.ConfirmationToken;
import com.securityservice.models.User;
import com.securityservice.repository.ConfirmationTokenRepository;
import com.securityservice.repository.UserRepository;
import com.securityservice.service.ConfirmationTokenService;
import com.securityservice.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SecurityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BindingResult bindingResult;

    @MockBean
    HttpSession httpSession;

    @MockBean
    ConfirmationTokenService confirmationTokenService;

    @MockBean
    EmailService emailService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ConfirmationTokenRepository confirmationTokenRepository;

    User user;
    ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("test");
        user.setId(1L);
        user.setPassword("testpass");
        user.setEmail("test@test.com");
        confirmationToken = new ConfirmationToken();
        confirmationToken.setToken("testToken");
        confirmationToken.setUserEmail(user.getEmail());
        confirmationToken.setId(1);
    }

    @Test
    void register() throws Exception {
        User user = new User();

        ResultActions response = mockMvc.perform(get("/v1/security/register"));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("userInfo", user));
    }

    @Test
    void testRegister() throws Exception {
        when(confirmationTokenService.createConfirmationToken(user.getEmail())).thenReturn("token");

        mockMvc.perform(post("/v1/security/registration")
                        .flashAttr("userInfo", user)
                        .sessionAttr("confirmationToken", new ConfirmationToken())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/v1/security/confirmation"));

        verify(emailService, times(1)).sendEmail(user.getEmail(), "token");
        verify(userRepository, times(1)).save(user);
        verify(confirmationTokenRepository, times(1)).save(any(ConfirmationToken.class));


    }

    @Test
    void confirmation() throws Exception {

        ResultActions response = mockMvc.perform(get("/v1/security/confirmation"));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("token", String.class));

    }


    @Test
    void login() throws Exception {
        ResultActions response = mockMvc.perform(get("/v1/security/login"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}