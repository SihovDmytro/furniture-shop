package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.entity.CartRequestBody;
import com.springtraining.furnitureshop.entity.CartResponseEntity;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.Attributes;
import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Parameters;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping(path = "/cart")
public class CartController {
    private final ProductService productService;
    private final CartService cartService;

    public CartController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }


    @GetMapping
    public String getCart(HttpSession session, Model model) {
        log.trace("getCart start");
        model.addAttribute(Attributes.CART, cartService.getCart(session));
        return Views.CART;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> addProduct(HttpSession session, @RequestBody Integer productID) {
        log.trace("addProduct start");
        log.info(Constants.LOGGER_FORMAT, Parameters.PRODUCT_ID, productID);
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(productID).map((product -> {
            log.info(Constants.LOGGER_FORMAT, Attributes.PRODUCT, product);
            cart.put(product, 1);
            return ResponseEntity.ok(CartResponseEntity.builder().size(cart.size()).build());
        })).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> removeProduct(HttpSession session, @RequestBody Long productID) {
        log.trace("removeProduct start");
        log.info(Constants.LOGGER_FORMAT, Parameters.PRODUCT_ID, productID);
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(productID).map((product -> {
            log.info(Constants.LOGGER_FORMAT, Attributes.PRODUCT, product);
            cart.remove(product);
            return ResponseEntity.ok((CartResponseEntity.builder().cartPrice(cart.getTotal()).build()));
        })).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> putProduct(HttpSession session,
                                                         @Valid @RequestBody CartRequestBody cartRequestBody,
                                                         Errors errors) {
        log.trace("putProduct start");
        log.info("change quantity of product with id={}, quantity={}",
                cartRequestBody.getProductID(),
                cartRequestBody.getQuantity());
        log.info(Constants.LOGGER_FORMAT, Attributes.ERRORS, errors);
        if (errors.getErrorCount() > 0) {
            return ResponseEntity.notFound().build();
        }
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(cartRequestBody.getProductID()).map((product -> {
            log.info(Constants.LOGGER_FORMAT, Attributes.PRODUCT, product);
            cart.put(product, cartRequestBody.getQuantity());
            return ResponseEntity.ok(CartResponseEntity.builder()
                    .total(cart.getTotalForProduct(product))
                    .cartPrice(cart.getTotal()).build());
        })).orElse(ResponseEntity.notFound().build());
    }
}
