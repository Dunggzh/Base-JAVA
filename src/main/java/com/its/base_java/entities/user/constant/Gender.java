package com.its.base_java.entities.user.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.its.base_java.dto.constant.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Giới tính: 0 - Nam, 1 - Nữ, 2 - Khác", type = "integer")
public enum Gender implements BaseEnum<Integer> {
    MALE,
    FEMALE,
    OTHER;

    @JsonCreator
    public static Gender fromValue(int value){
        for (Gender column: values()){
            if(column.toValue()==value)
                return column;
        }
        return null;
    }

    @JsonValue
    public Integer toValue() {
        return ordinal();
    }
}
