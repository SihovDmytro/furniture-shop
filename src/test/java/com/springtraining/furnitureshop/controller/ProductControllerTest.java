package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.CategoryService;
import com.springtraining.furnitureshop.service.ProducerService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.PaginationProps;
import com.springtraining.furnitureshop.util.ProductProps;
import com.springtraining.furnitureshop.util.Views;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProducerService producerService;

    @Mock
    private CartService cartService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PaginationProps pageProps = new PaginationProps(List.of("5", "10", "25"), 4);
        ProductProps productProps = new ProductProps(1, 5, Sort.Direction.DESC, "price");

        ProductController controller = new ProductController(
                productService, categoryService, producerService, pageProps, cartService, productProps);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".html"))
                .build();

        when(categoryService.findAll()).thenReturn(List.of(new Category("Seating")));
        when(producerService.findAll()).thenReturn(List.of(new Producer("ACME")));
        when(cartService.count(any())).thenReturn(0);

        Product product = new Product("Chair", new BigDecimal("99.00"),
                new Category("Seating"), new Producer("ACME"), "desc", "img.png");
        Page<Product> page = new PageImpl<>(List.of(product), PageRequest.of(0, 5), 1);
        when(productService.getProducts(any())).thenReturn(page);
    }

    @Test
    void getProductsPage_shouldReturn200AndProductsView() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name(Views.PRODUCTS))
                .andExpect(model().attributeExists(Attributes.PRODUCTS))
                .andExpect(model().attributeExists(Attributes.PAGE_NUMBERS));
    }

    @Test
    void getProductsPage_shouldReturn200WithFilterParams() throws Exception {
        mockMvc.perform(get("/products")
                        .param("name", "Chair")
                        .param("minPrice", "50"))
                .andExpect(status().isOk())
                .andExpect(view().name(Views.PRODUCTS));
    }
}







