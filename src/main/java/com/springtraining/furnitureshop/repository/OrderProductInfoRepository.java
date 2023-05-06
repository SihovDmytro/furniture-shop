package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.OrderProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductInfoRepository extends JpaRepository<OrderProductInfo, OrderProductInfo.Id> {


}
