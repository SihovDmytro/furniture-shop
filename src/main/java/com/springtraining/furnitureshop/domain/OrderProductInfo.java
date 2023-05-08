package com.springtraining.furnitureshop.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders_product_info")
public class OrderProductInfo {
    @EmbeddedId
    private Id id = new Id();

    private int quantity;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @MapsId("productInfoId")
    @JoinColumn(name = "product_info_ID", insertable = false, updatable = false)
    private ProductInfo productInfo;

    public OrderProductInfo(int quantity, Order order, ProductInfo productInfo) {
        this.quantity = quantity;
        this.order = order;
        this.productInfo = productInfo;
    }

    protected OrderProductInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductInfo that = (OrderProductInfo) o;
        return getQuantity() == that.quantity
                && Objects.equals(getOrder(), that.order)
                && Objects.equals(getProductInfo(), that.productInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getOrder(), getProductInfo());
    }

    @Override
    public String toString() {
        return "OrderProductInfo{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", order=" + order +
                ", productInfo=" + productInfo +
                '}';
    }

    public Id getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "order_id")
        private Long orderId;
        @Column(name = "product_info_ID")
        private Long productInfoId;

        public Id() {
        }

        public Id(Long orderId, Long productInfoId) {
            this.orderId = orderId;
            this.productInfoId = productInfoId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return Objects.equals(orderId, id.orderId)
                    && Objects.equals(productInfoId, id.productInfoId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productInfoId);
        }
    }
}
