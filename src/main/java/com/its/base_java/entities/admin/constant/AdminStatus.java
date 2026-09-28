package com.its.base_java.entities.admin.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.its.base_java.dto.constant.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Trạng thái của tài khoản admin: 0 - INACTIVE, 1 - ACTIVE", type = "integer")
public enum AdminStatus implements BaseEnum<Integer> {
    INACTIVE,
    ACTIVE;

    @JsonCreator
    public static AdminStatus fromValue(int value) {
        for (AdminStatus column : values()) {
            if (column.toValue() == value) {
                return column;
            }
        }
        return null;
    }

    @JsonValue
    public Integer toValue() {
        return ordinal();
    }

}
