package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCriteria {
    long countProducts(ProductBean bean);

    Page<Product> getProductsPageable(ProductBean bean);
}
