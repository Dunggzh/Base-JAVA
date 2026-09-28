package com.its.base_java.dto.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public interface BaseEnum<T> {
     @JsonValue
     T toValue();
}
