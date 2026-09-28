package com.its.base_java.dto.constant;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Các loại mẫu QR SePay")
public enum QrTemplate implements BaseEnum<String> {
    DEFAULT(""),
    COMPACT("compact"),
    QR_ONLY("qronly");

    private final String value;

    QrTemplate(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    @JsonCreator
    public static QrTemplate fromValue(String v) {
        if (v == null || v.isBlank()) return DEFAULT;
        for (QrTemplate t : values()) {
            if (t.value.equalsIgnoreCase(v) || t.name().equalsIgnoreCase(v)) {
                return t;
            }
        }
        return DEFAULT;
    }

    public boolean isDefault() { return this == DEFAULT; }
}
