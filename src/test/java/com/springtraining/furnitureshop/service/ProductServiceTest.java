package com.springtraining.furnitureshop.service;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final long PRODUCT_ID = 1L;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product buildProduct() {
        return new Product("Chair", new BigDecimal("99.00"),
                new Category("Seating"), new Producer("ACME"), "desc", "img.png");
    }

    @Test
    void getProducts_shouldDelegateToRepository() {
        ProductBean bean = ProductBean.builder().build();
        Page<Product> page = new PageImpl<>(List.of(buildProduct()));
        when(productRepository.getProductsPageable(bean)).thenReturn(page);

        Page<Product> result = productService.getProducts(bean);

        assertEquals(1, result.getContent().size());
        verify(productRepository, times(1)).getProductsPageable(bean);
    }

    @Test
    void countProducts_shouldDelegateToRepository() {
        ProductBean bean = ProductBean.builder().build();
        when(productRepository.countProducts(bean)).thenReturn(5L);

        long result = productService.countProducts(bean);

        assertEquals(5L, result);
        verify(productRepository, times(1)).countProducts(bean);
    }

    @Test
    void getProduct_shouldReturnProductById() {
        Product product = buildProduct();
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProduct(PRODUCT_ID);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(PRODUCT_ID);
    }

    @Test
    void getProduct_shouldReturnEmptyWhenNotFound() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProduct(PRODUCT_ID);

        assertTrue(result.isEmpty());
    }
}

