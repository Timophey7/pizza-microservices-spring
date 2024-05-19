package com.menuservice.menuservice.config.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.menuservice.menuservice.model.CartResponse;
import com.menuservice.menuservice.model.Order;
import com.menuservice.menuservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final OrderRepository orderRepository;


    @KafkaListener(topics = "pizza-order",groupId = "orders")
    public void listen(CartResponse cartResponse) {
        Order order = mapToOrder(cartResponse);
        orderRepository.save(order);

    }

    public Order mapToOrder(CartResponse cartResponse) {
        Order order = new Order();
        order.setProductName(cartResponse.getProductName());
        order.setProductPrice(cartResponse.getProductPrice());
        order.setProductUrl(cartResponse.getProductUrl());
        return order;
    }

}
