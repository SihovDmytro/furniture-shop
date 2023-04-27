package com.springtraining.furnitureshop.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "product_info", schema = "webshop")
public class ProductInfo {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "product_info_id", nullable = false)
    private Long id;
    // TODO: 11.03.2023 refactor class
    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "price", nullable = false, precision = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Producer producer;

    @Column(name = "description", nullable = true, length = 100)
    private String description;

    @Column(name = "image", nullable = true, length = 45)
    private String image;
}
