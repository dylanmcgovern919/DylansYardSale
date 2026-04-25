package com.dylansyardsale.model;

// ENUM required for order status tracking 
public enum OrderStatus {
    PROCESSING, SHIPPED, COMPLETED 
    // These are the allowed status values used by business logic, validation, and API responses.
}
