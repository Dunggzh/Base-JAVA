package com.its.base_java.entities.role.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.its.base_java.dto.constant.BaseEnum;

public enum PermissionType implements BaseEnum<String> {

    // Admin + agency Area
    ADMIN_DASHBOARD,
    ADMIN_ROLE,
    ADMIN_ACCOUNT,
    NUMBER_MANAGEMENT,
    CARRIER_ORDER_MANAGEMENT,
    BOOKING_MANAGEMENT,
    NUMBER_AUCTION_MANAGEMENT,
    NUMBER_BLOCK_MANAGEMENT,
    NUMBER_DEACTIVATION_MANAGEMENT,
    BRANDNAME_MANAGEMENT,
    CUSTOMER_CLUSTER_MANAGEMENT,
    CUSTOMER_MANAGEMENT,
    VENDOR_MANAGEMENT,
    CANCELLED_HUB_TICKET_MANAGER,
    AGENCY_MANAGEMENT;

    @JsonCreator
    public static PermissionType fromValue(String value) {
        for (PermissionType column : values()) {
            if (column.toValue().equals(value)) {
                return column;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toValue() {
        return name();
    }
}
