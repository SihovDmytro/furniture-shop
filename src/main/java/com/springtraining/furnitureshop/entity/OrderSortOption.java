package com.springtraining.furnitureshop.entity;

import com.springtraining.furnitureshop.util.LocalizationTags;

public enum OrderSortOption {
    PRICE(LocalizationTags.ORDER_TOTAL_PRICE),
    ITEMS(LocalizationTags.ORDER_ITEMS),
    STATUS(LocalizationTags.ORDER_STATUS),
    DATE(LocalizationTags.ORDER_DATE);

    OrderSortOption(String localizationTag) {
        this.localizationTag = localizationTag;
    }

    private final String localizationTag;

    public String getLocalizationTag() {
        return localizationTag;
    }

}
