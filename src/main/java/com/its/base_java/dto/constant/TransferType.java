package com.its.base_java.dto.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Hướng giao dịch: in=tiền vào, out=tiền ra", allowableValues = {"in", "out"})
public enum TransferType implements BaseEnum<String> {
    IN("in"),
    OUT("out");

    private final String value;

    TransferType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TransferType fromValue(String value) {
        if (value == null) return null;
        for (TransferType t : values()) {
            if (t.value.equalsIgnoreCase(value) || t.name().equalsIgnoreCase(value)) {
                return t;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
