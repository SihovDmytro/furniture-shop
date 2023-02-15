package com.springtraining.furnitureshop.repository;

import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;

import java.util.List;

public interface ProductRepositoryCriteria {
    long countProducts(ProductBean bean);

    List<Product> getProducts(ProductBean bean);
}
