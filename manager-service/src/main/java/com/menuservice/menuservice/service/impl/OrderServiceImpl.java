package com.menuservice.menuservice.service.impl;

import com.menuservice.menuservice.model.Order;
import com.menuservice.menuservice.repository.OrderRepository;
import com.menuservice.menuservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

}
