package com.springtraining.furnitureshop.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "statusDescription", nullable = true, length = 45)
    private String statusDescription;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_product_info",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_info_id")})
    private List<ProductInfo> products = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    public enum OrderStatus {
        ACCEPTED, CONFIRMED, FORMED, PROCESSING, SENT, COMPLETED, CANCELED
    }

    public List<ProductInfo> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
