package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.entity.OrderDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(long userId);
//select sum(opi.quantity*pi.price) as 'totalPrice',count(o.order_id) as 'items', o.status, o.status_description as 'statusDescription', o.date from orders o join orders_product_info opi on o.order_id=opi.order_ID join product_info pi on opi.product_info_ID=pi.product_info_id join user u on o.user_ID=u.id where u.login= ?1 group by o.order_id order by ?2
    @Query( value = "select sum(o.products.),count(o.order_id), o.status, o.status_description, o.date from Order o join Or opi on o.order_id=opi.order_ID join product_info pi on opi.product_info_ID=pi.product_info_id join user u on o.user_ID=u.id where u.login= ?1 group by o.order_id order by ?2")
    List<OrderDto> findDtoByUserLogin(String login, String sortField, String direction);
}
