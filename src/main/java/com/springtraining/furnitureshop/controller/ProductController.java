package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.Product_;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.entity.SortOrder;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.CategoryService;
import com.springtraining.furnitureshop.service.ProducerService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.ProductPageProps;
import com.springtraining.furnitureshop.util.ProductProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProducerService producerService;
    private final CartService cartService;
    private final ProductPageProps pageProps;
    private final ProductProps productProps;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             ProducerService producerService,
                             ProductPageProps pageProps,
                             CartService cartService,
                             ProductProps productProps) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.producerService = producerService;
        this.pageProps = pageProps;
        this.cartService = cartService;
        this.productProps = productProps;
    }

    @ModelAttribute(name = "pageProperties")
    public ProductPageProps pageProperties() {
        return pageProps;
    }

    @ModelAttribute(name = "sortFields")
    public List<String> sortOptions() {
        return Arrays.asList(
                Product_.PRICE,
                Product_.NAME,
                Product_.PRODUCER,
                Product_.CATEGORY);
    }

    @ModelAttribute(name = "productsInCart")
    public int productsInCart() {
        return cartService.count();
    }

    @ModelAttribute(name = "sortOrders")
    public List<SortOrder> sortOrders() {
        return Arrays.asList(SortOrder.DESCENDING, SortOrder.ASCENDING);
    }

    @ModelAttribute(name = "categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute(name = "producers")
    public List<Producer> producers() {
        return producerService.findAll();
    }

    @ModelAttribute(name = "productBean")
    public ProductBean productBean() {
        return new ProductBean();
    }

    @GetMapping
    public String getProductsPage(ProductBean productBean, Model model) {
        log.info("getProductsPage() invoked with productBean: " + productBean);
        setRequiredProperties(productBean);
        List<Product> products = productService.getProducts(productBean);
        log.trace("found list of products: " + products);
        model.addAttribute("products", products);
        return "products";
    }

    private void setRequiredProperties(ProductBean productBean) {
        if (productBean.getPage() == null) {
            productBean.setPage(productProps.getPage());
        }
        if (productBean.getPageSize() == null) {
            productBean.setPageSize(productProps.getPageSize());
        }
        if (productBean.getSortField() == null) {
            productBean.setSortField(productProps.getSortField());
        }
        if (productBean.getSortOrder() == null) {
            productBean.setSortOrder(productProps.getSortOrder());
        }
    }
}
