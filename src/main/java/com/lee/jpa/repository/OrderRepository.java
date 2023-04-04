package com.lee.jpa.repository;

import com.lee.jpa.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order fineOne(Long orderId){
        return em.find(Order.class, orderId);
    }
}