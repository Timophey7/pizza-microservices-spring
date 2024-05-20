package com.menuservice.menuservice.service;

import com.menuservice.menuservice.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();

}
