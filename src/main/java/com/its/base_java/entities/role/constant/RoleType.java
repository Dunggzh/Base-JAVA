package com.its.base_java.entities.role.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.its.base_java.dto.constant.BaseEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Trạng thái:  1 - ADMIN, 2 - QLKS, 3 - SALE_ADMIN", type = "integer")
public enum RoleType implements BaseEnum<Integer> {
    ADMIN(1),
    QLKS(2),
    SALE_ADMIN(3);


    final int value;

    RoleType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static RoleType fromValue(int value) {
        for (RoleType column : RoleType.values()) {
            if (column.value == value) {
                return column;
            }
        }
        return null;
    }

    @JsonValue
    public Integer toValue() {
        return value;
    }
}
