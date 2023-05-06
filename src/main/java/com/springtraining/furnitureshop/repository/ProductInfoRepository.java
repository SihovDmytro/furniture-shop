package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

    @Query("select pi from ProductInfo pi join OrderProductInfo opi on pi.id=opi.id.productInfoId where opi.id.orderId=?1")
    List<ProductInfo> findProductInfoByOrderId(Long orderId);
}
