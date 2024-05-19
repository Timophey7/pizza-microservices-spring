package com.securityservice.controller;

import com.securityservice.models.ConfirmationToken;
import com.securityservice.models.Role;
import com.securityservice.models.User;
import com.securityservice.repository.ConfirmationTokenRepository;
import com.securityservice.repository.UserRepository;
import com.securityservice.service.ConfirmationTokenService;
import com.securityservice.service.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;



@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/security")
public class SecurityController {

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository tokenRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userInfo", new User());
        return "registration-page";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("userInfo") User user, BindingResult bindingResult,HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "redirect:/v1/security/register";
        }
        String confirmationToken = confirmationTokenService.createConfirmationToken(user.getEmail());
        emailService.sendEmail(user.getEmail(), confirmationToken);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        ConfirmationToken token = new ConfirmationToken();
        token.setExpiryDate(new Date(new Date().getTime() + 1000 * 60 * 15));
        token.setToken(confirmationToken);
        token.setUserEmail(user.getEmail());
        tokenRepository.save(token);
        session.setAttribute("confirmationToken", token);
        return "redirect:/v1/security/confirmation";

    }

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        model.addAttribute("token",String.class);
        return "confirmation-page";
    }

    @PostMapping("/confrimToken")
    public String confrimToken(@Valid @ModelAttribute("token") String token, BindingResult bindingResult,HttpSession session) {
        if (bindingResult.hasErrors()){
            return "redirect:/v1/security/confirmation";
        }
        ConfirmationToken confirmationToken = (ConfirmationToken) session.getAttribute("confirmationToken");
        if (confirmationToken.getToken().equals(token)){
            try{
                User user = userRepository.findUserByEmail(confirmationToken.getUserEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
                user.setEmailVerified(true);
                userRepository.save(user);
                return "redirect:/v1/security/login";
            }catch (Exception e){
                return "redirect:/v1/security/confirmation";
            }
        }
        return "redirect:/v1/security/confirmation";

    }

    @GetMapping("/login")
    public String login() {
        return "login-page";
    }

}
