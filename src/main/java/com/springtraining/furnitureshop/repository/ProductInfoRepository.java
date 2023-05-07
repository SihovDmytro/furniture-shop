package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {

    @Query("select pi from ProductInfo pi where pi.price=?1 and pi.name=?2 and pi.category=?3 and pi.producer=?4 and pi.description=?5")
    Optional<ProductInfo> findFromProduct(BigDecimal price, String name, Category category, Producer producer, String description);
}
