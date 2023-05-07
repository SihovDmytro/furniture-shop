package com.springtraining.furnitureshop.domain;


import com.springtraining.furnitureshop.util.DateUtil;
import com.springtraining.furnitureshop.util.LocalizationTags;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Locale;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(optional = false)
    private User user;

    public Order(OrderStatus status, String statusDescription, Calendar date, User user) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.date = date;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", statusDescription='" + statusDescription + '\'' +
                ", date=" + DateUtil.dateToString(date, Locale.getDefault()) +
                ", user=" + user +
                '}';
    }

    public enum OrderStatus {
        ACCEPTED(LocalizationTags.ORDER_STATUS_ACCEPTED),
        CONFIRMED(LocalizationTags.ORDER_STATUS_CONFIRMED),
        FORMED(LocalizationTags.ORDER_STATUS_FORMED),
        PROCESSING(LocalizationTags.ORDER_STATUS_PROCESSING),
        SENT(LocalizationTags.ORDER_STATUS_SENT),
        COMPLETED(LocalizationTags.ORDER_STATUS_COMPLETED),
        CANCELED(LocalizationTags.ORDER_STATUS_CANCELED);

        OrderStatus(String localizationTag) {
            this.localizationTag = localizationTag;
        }

        private final String localizationTag;

        public String getLocalizationTag() {
            return localizationTag;
        }
    }
}
