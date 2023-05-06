package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Order;
import com.springtraining.furnitureshop.entity.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true, value = "select o.id, sum(opi.quantity*pi.price) as 'price',count(o.id) as 'items', o.status as 'status', o.status_description as 'statusDescription', o.date as 'date' from orders o join orders_product_info opi on o.id=opi.order_ID join product_info pi on opi.product_info_ID=pi.id join user u on o.user_ID=u.id where u.login= ?1 group by o.id")
    Page<OrderDto> findByUserLogin(String login, Pageable pageable);
}
