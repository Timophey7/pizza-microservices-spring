package com.menuservice.menuservice.service.impl;

import com.menuservice.menuservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 22 * * ?")
    public void cleanOrdersList(){
        orderRepository.deleteAll();
    }


}
