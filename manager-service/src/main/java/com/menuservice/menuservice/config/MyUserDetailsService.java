package com.menuservice.menuservice.config;

import com.menuservice.menuservice.model.Manager;
import com.menuservice.menuservice.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private  SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String managerName) throws UsernameNotFoundException {
        Optional<Manager> manager = securityRepository.findByManagerName(managerName);

        return manager.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("пользователь не найден"));
    }
}
