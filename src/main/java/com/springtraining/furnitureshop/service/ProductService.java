package com.springtraining.furnitureshop.service;


import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Constructs the service with its required repository.
     *
     * @param productRepository the repository used to query product data
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Returns a paginated and filtered list of products matching the criteria in the given bean.
     *
     * @param bean the filter/sort/pagination parameters; must not be {@code null}
     * @return a {@link Page} of {@link Product} results
     */
    public Page<Product> getProducts(ProductBean bean) {
        return productRepository.getProductsPageable(bean);
    }

    /**
     * Returns the total number of products that match the criteria in the given bean.
     *
     * @param bean the filter parameters; must not be {@code null}
     * @return the count of matching products
     */
    public long countProducts(ProductBean bean) {
        return productRepository.countProducts(bean);
    }

    /**
     * Retrieves a single product by its primary key.
     *
     * @param id the database ID of the product to look up
     * @return an {@link Optional} containing the matching {@link Product}, or empty if not found
     */
    public Optional<Product> getProduct(long id) {
        return productRepository.findById(id);
    }
}
