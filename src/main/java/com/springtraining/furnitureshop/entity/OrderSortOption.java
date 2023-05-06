package com.springtraining.furnitureshop.entity;

public enum OrderSortOption {
    PRICE("ordersPage.totalPrice"),
    ITEMS("ordersPage.items"),
    STATUS("ordersPage.status"),
    DATE("ordersPage.date");

    OrderSortOption(String localizationTag) {
        this.localizationTag = localizationTag;
    }

    private final String localizationTag;

    public String getLocalizationTag() {
        return localizationTag;
    }

}
