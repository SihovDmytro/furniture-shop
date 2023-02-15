package com.springtraining.furnitureshop.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = "id")
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "product")
public class Product {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private @NonNull String name;

    @Column(name = "price", nullable = false, precision = 2)
    private @NonNull BigDecimal price;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    private @NonNull Category category;

    @ManyToOne(cascade = {CascadeType.ALL}, optional = false)
    private @NonNull Producer producer;

    @Column(name = "description", nullable = true, length = 100)
    private @NonNull String description;

    @Column(name = "image", nullable = true, length = 45)
    private @NonNull String image;
}
