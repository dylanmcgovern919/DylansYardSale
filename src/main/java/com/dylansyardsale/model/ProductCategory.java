package com.dylansyardsale.model;

import com.fasterxml.jackson.annotation.JsonCreator;

// ENUM required for category handling //REQUIRED - Standardizes allowed product categories and avoids text inconsistency.
public enum ProductCategory {
    CLOTHING, RECORD, COMIC; //REQUIRED - Current supported inventory categories.

    @JsonCreator
    public static ProductCategory fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return ProductCategory.valueOf(value.trim().toUpperCase());
    }
}
