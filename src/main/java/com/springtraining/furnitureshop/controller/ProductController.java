package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.domain.Category;
import com.springtraining.furnitureshop.domain.Producer;
import com.springtraining.furnitureshop.domain.Product;
import com.springtraining.furnitureshop.domain.Product_;
import com.springtraining.furnitureshop.entity.ProductBean;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.CategoryService;
import com.springtraining.furnitureshop.service.ProducerService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.ProductPageProps;
import com.springtraining.furnitureshop.util.ProductProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    public int productsInCart(HttpSession session) {
        return cartService.count(session);
    }

    @ModelAttribute(name = "sortOrders")
    public List<Direction> sortOrders() {
        return Arrays.asList(Direction.DESC, Direction.ASC);
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

    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @ModelAttribute(name = "filters")
    public String filters(@RequestParam MultiValueMap<String, String> params) {
        params.remove("page");
        params.remove("size");
        log.info(params.toString());
        StringBuilder filters = new StringBuilder();
        for (Map.Entry<String, List<String>> parameter : params.entrySet()) {
            for (String value : parameter.getValue()) {
                filters.append("&");
                filters.append(parameter.getKey()).append("=").append(value);
            }
        }
        log.info(filters.toString());
        return filters.toString();
    }

    @GetMapping
    public String getProductsPage(@Valid ProductBean productBean, Model model) {
        log.info("getProductsPage() invoked with productBean: " + productBean);
        setRequiredProperties(productBean);
        Page<Product> products = productService.getProducts(productBean);
        log.info(String.format("found list of products(%s): %s", products.getSize(), products));
        model.addAttribute("products", products);

        List<Integer> pageNumbers = getPageNumbers(products.getTotalPages(), products.getPageable().getPageNumber());
        log.info("pageNumbers: " + pageNumbers);
        model.addAttribute("pageNumbers", pageNumbers);
        return "products";
    }

    private void setRequiredProperties(ProductBean productBean) {
        if (productBean.getPage() == null) {
            productBean.setPage(productProps.getPage());
        }
        if (productBean.getSize() == null) {
            productBean.setSize(productProps.getSize());
        }
        if (productBean.getSortField() == null) {
            productBean.setSortField(productProps.getSortField());
        }
        if (productBean.getSortOrder() == null) {
            productBean.setSortOrder(productProps.getSortOrder());
        }
    }

    private List<Integer> getPageNumbers(int totalPages, int currPage) {
        List<Integer> pages = new ArrayList<>();
        int range = pageProps.getPaginationRange();
        pages.add(1);
        if (totalPages > 1) {
            for (int i = Math.max(currPage - range + 1, 2); i < totalPages && currPage + range >= i - 1; i++) {
                pages.add(i);
            }
            pages.add(totalPages);
        }
        return pages;
    }
}
