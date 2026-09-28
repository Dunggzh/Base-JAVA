package com.its.base_java.dto.response;
import java.util.Map;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseHubResponse {
    private boolean status;
    private Map errors = Maps.newHashMap();
    private String message = "Success";
}
