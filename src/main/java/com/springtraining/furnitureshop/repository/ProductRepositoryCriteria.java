package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCriteria {
    long countProducts(ProductBean bean);

    List<Product> getProducts(ProductBean bean);

    Page<Product> getProductsPageable(ProductBean bean);
}
