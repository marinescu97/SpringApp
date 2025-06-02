package com.springApp.Jpa.service;

import com.springApp.Jpa.entity.Order;
import com.springApp.Jpa.entity.User;
import com.springApp.Jpa.repository.OrderRepository;
import com.springApp.Jpa.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public OrderService(OrderRepository repository, UserJpaRepository userJpaRepository) {
        this.repository = repository;
        this.userJpaRepository = userJpaRepository;
    }

    public Order placeOrder(Long userId, String product, Double price) {
        Optional<User> userOptional = userJpaRepository.findById(userId);
        Order order = null;

        if (userOptional.isPresent()){
            User user = userOptional.get();
            order = new Order(product, price, LocalDate.now(), user);
        } else {
            System.out.println("User not found for id " + userId);
        }

        return repository.save(order);
    }

    public List<Order> getOrdersWithPagination(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Order> orderPage = repository.findByUserId(userId, pageRequest);
        return orderPage.getContent();
    }
}
