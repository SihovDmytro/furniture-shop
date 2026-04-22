package com.springtraining.furnitureshop.repository.impl;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.Product_;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.repository.CategoryRepository;
import com.springtraining.furnitureshop.repository.ProducerRepository;
import com.springtraining.furnitureshop.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProductRepositoryCriteriaImplTest {

    private static final int SECOND_PAGE = 2;
    private static final int PAGE_SIZE = 2;
    private static final String FILTER_CATEGORY = "cat1";
    private static final String FILTER_NAME = "product1";
    private static final String FILTER_MIN_PRICE_PRODUCT = "product6";
    private static final BigDecimal MIN_PRICE_FILTER = new BigDecimal("6");

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("product5", new BigDecimal("5.00"), new Category("cat5"), new Producer("prod5"), "desk5", "img5"));
        products.add(new Product("product1", new BigDecimal("1.00"), new Category("cat1"), new Producer("prod1"), "desk1", "img1"));
        products.add(new Product("product3", new BigDecimal("3.00"), new Category("cat3"), new Producer("prod3"), "desk3", "img3"));
        products.add(new Product("product4", new BigDecimal("4.00"), new Category("cat4"), new Producer("prod4"), "desk4", "img4"));
        products.add(new Product("product2", new BigDecimal("2.00"), new Category("cat2"), new Producer("prod2"), "desk2", "img2"));
        products.add(new Product("product6", new BigDecimal("6.00"), new Category("cat6"), new Producer("prod6"), "desk6", "img6"));
        return products;
    }

    @BeforeEach
    void setUp() {
        productRepository.saveAll(getProducts());
    }

    @Test
    void shouldReturnFilteredProducts() {
        List<Product> products1 = productRepository.getProductsPageable(
                ProductBean.builder()
                        .minPrice(MIN_PRICE_FILTER)
                        .build()).getContent();
        List<Product> products2 = productRepository.getProductsPageable(
                ProductBean.builder()
                        .producers(producerRepository.findAll())
                        .category(categoryRepository.findByName(FILTER_CATEGORY))
                        .name(FILTER_NAME)
                        .build()).getContent();
        List<Product> products3 = productRepository.getProductsPageable(
                ProductBean.builder()
                        .page(SECOND_PAGE)
                        .size(PAGE_SIZE)
                        .sortField(Product_.PRICE)
                        .sortOrder(Sort.Direction.DESC)
                        .build()).getContent();

        List<Product> expected = getProducts().subList(2, 4);
        expected.sort(Comparator.comparing(Product::getPrice).reversed());
        boolean secondPageSortDescending = products3.containsAll(expected)
                && expected.get(0).getName().equals(products3.get(0).getName())
                && expected.get(1).getName().equals(products3.get(1).getName());

        assertAll(
                () -> assertEquals(FILTER_MIN_PRICE_PRODUCT, products1.get(0).getName()),
                () -> assertEquals(FILTER_NAME, products2.get(0).getName()),
                () -> assertTrue(secondPageSortDescending)
        );
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }
}