package com.lee.jpa.service;

import com.lee.jpa.domain.Address;
import com.lee.jpa.domain.Member;
import com.lee.jpa.domain.Order;
import com.lee.jpa.domain.OrderStatus;
import com.lee.jpa.domain.item.Book;
import com.lee.jpa.domain.item.Item;
import com.lee.jpa.exception.NotEnoughStockException;
import com.lee.jpa.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문(){
        // given
        Member member = new Member("회원1", new Address("서울", "강가", "123-123"));
        em.persist(member);
        Book book = new Book("시골 JPA", 10000, 10);
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.fineOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        Assertions.assertEquals(1, getOrder.getOrderItems().size());
        Assertions.assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        Assertions.assertEquals(8, book.getStockQuantity());

    }

    @Test
    void 상품주문_재고수량초과(){
        // given
        Member member = new Member("회원1", new Address("서울", "강가", "123-123"));
        em.persist(member);
        Item item = new Book("시골 JPA", 10000, 10);
        em.persist(item);

        int orderCount = 11;

        // when
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    void 주문취소(){
        // given
        Member member = new Member("회원1", new Address("서울", "강가", "123-123"));
        em.persist(member);
        Book item = new Book("시골 JPA", 10000, 10);
        em.persist(item);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.fineOne(orderId);

        Assertions.assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        Assertions.assertEquals(10, item.getStockQuantity());
    }
}