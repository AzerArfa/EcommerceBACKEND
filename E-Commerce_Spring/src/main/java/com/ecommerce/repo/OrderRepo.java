package com.ecommerce.repo;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

     Order findByUserAndStatus(User user, OrderStatus status);

     Order findByUserIdAndStatus(Long userId, OrderStatus status);

     List<Order> findAllByUserIdAndStatus(Long userId, OrderStatus submitted);

     List<Order> findAllByStatus(OrderStatus submitted);

}
