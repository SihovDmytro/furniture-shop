package com.springtraining.furnitureshop.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;


@Data
@NoArgsConstructor()
@Entity
@Table(name = "orders")
public class Order {
    @GeneratedValue(generator = "ID_GENERATOR")
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "status_description", nullable = true, length = 45)
    private String statusDescription;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public enum OrderStatus {
        ACCEPTED("order.status.accepted"),
        CONFIRMED("order.status.confirmed"),
        FORMED("order.status.formed"),
        PROCESSING("order.status.processing"),
        SENT("order.status.sent"),
        COMPLETED("order.status.completed"),
        CANCELED("order.status.canceled");

        OrderStatus(String localizationTag) {
            this.localizationTag = localizationTag;
        }

        private final String localizationTag;

        public String getLocalizationTag() {
            return localizationTag;
        }
    }
}
