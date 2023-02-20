package com.springtraining.furnitureshop.controller;

import com.springtraining.furnitureshop.entity.CartRequestBody;
import com.springtraining.furnitureshop.entity.CartResponseEntity;
import com.springtraining.furnitureshop.entity.ShoppingCart;
import com.springtraining.furnitureshop.service.CartService;
import com.springtraining.furnitureshop.service.ProductService;
import com.springtraining.furnitureshop.util.Attributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

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

    @ModelAttribute(name = "locale")
    public Locale locale(Locale locale) {
        return locale;
    }

    @GetMapping
    public String getCart(HttpSession session, Model model) {
        model.addAttribute(Attributes.CART, cartService.getCart(session));
        return "cart";
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> addProduct(HttpSession session, @RequestBody Integer productID) {
        log.info("add product to cart with id=" + productID);
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(productID).map((product -> {
            log.info("found product: " + product);
            cart.put(product, 1);
            return ResponseEntity.ok(CartResponseEntity.builder().size(cart.size()).build());
        })).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> removeProduct(HttpSession session, @RequestBody Long productID) {
        log.info("remove product from cart with id=" + productID);
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(productID).map((product -> {
            log.info("found product: " + product);
            cart.remove(product);
            return ResponseEntity.ok((CartResponseEntity.builder().cartPrice(cart.getTotal()).build()));
        })).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CartResponseEntity> putProduct(HttpSession session,
                                                         @Valid @RequestBody CartRequestBody cartRequestBody,
                                                         Errors errors) {
        log.info(String.format("change quantity of product with id=%s, quantity=%s",
                cartRequestBody.getProductID(),
                cartRequestBody.getQuantity()));
        log.info("errors: " + errors.getErrorCount());
        if (errors.getErrorCount() > 0) {
            return ResponseEntity.notFound().build();
        }
        ShoppingCart cart = cartService.getCart(session);
        return productService.getProduct(cartRequestBody.getProductID()).map((product -> {
            log.info("found product: " + product);
            cart.put(product, cartRequestBody.getQuantity());
            return ResponseEntity.ok(CartResponseEntity.builder()
                    .total(cart.getTotalForProduct(product))
                    .cartPrice(cart.getTotal()).build());
        })).orElse(ResponseEntity.notFound().build());
    }
}
