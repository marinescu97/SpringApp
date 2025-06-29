package com.springApp.Jpa.repository;

import com.springApp.Jpa.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, PageRequest pageRequest);
    Order findByUserId(Long userId);
}
