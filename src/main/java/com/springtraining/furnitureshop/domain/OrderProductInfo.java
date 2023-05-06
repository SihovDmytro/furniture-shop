package com.springtraining.furnitureshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor()
@Entity
@Table(name = "orders_product_info")
public class OrderProductInfo {
    @EmbeddedId
    private Id id = new Id();

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productInfoId")
    @JoinColumn(name = "product_info_ID", insertable = false, updatable = false)
    private ProductInfo productInfo;

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
            return Objects.equals(orderId, id.orderId) && Objects.equals(productInfoId, id.productInfoId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, productInfoId);
        }
    }
}
