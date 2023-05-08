package com.springtraining.furnitureshop.domain;

import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "id", nullable = false)
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

    public Product(@NonNull String name,
                   @NonNull BigDecimal price,
                   @NonNull Category category,
                   @NonNull Producer producer,
                   @NonNull String description,
                   @NonNull String image) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.producer = producer;
        this.description = description;
        this.image = image;
    }

    protected Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getName().equals(product.name)
                && getPrice().equals(product.price)
                && getCategory().equals(product.category)
                && getProducer().equals(product.producer)
                && getDescription().equals(product.description)
                && getImage().equals(product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getPrice(),
                getCategory(),
                getProducer(),
                getDescription(),
                getImage());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", producer=" + producer +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
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
