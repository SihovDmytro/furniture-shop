package com.springtraining.furnitureshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private Producer producer;

    @Column(name = "description", nullable = true, length = 100)
    private String description;

    public ProductInfo(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.producer = product.getProducer();
        this.description = product.getDescription();
    }
}
