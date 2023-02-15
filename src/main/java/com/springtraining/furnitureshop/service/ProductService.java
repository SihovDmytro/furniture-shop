package com.springtraining.furnitureshop.service;


import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(ProductBean bean) {
        return productRepository.getProducts(bean);
    }

    public long countProducts(ProductBean bean) {
        return productRepository.countProducts(bean);
    }

    public Optional<Product> getProduct(long id) {
        return productRepository.findById(id);
    }
}