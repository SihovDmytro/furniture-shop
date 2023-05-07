package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.OrderProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductInfoRepository extends JpaRepository<OrderProductInfo, OrderProductInfo.Id> {

    @Query("select opi from OrderProductInfo opi where opi.id.orderId=?1")
    List<OrderProductInfo> findProductInfoByOrderId(Long orderId);
}
