package com.springtraining.furnitureshop.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@EqualsAndHashCode(exclude = "id")
@ToString
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

    public Product(@NonNull String name, @NonNull BigDecimal price, @NonNull Category category, @NonNull Producer producer, @NonNull String description, @NonNull String image) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.producer = producer;
        this.description = description;
        this.image = image;
    }

    private Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }
}
