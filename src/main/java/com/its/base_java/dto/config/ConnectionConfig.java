package com.its.base_java.dto.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ConnectionConfig {
    private String apiUrl;
    private String clientId;
    private String oauthUrl;
    private String redirectUri;
    private String secretKey;

    public String getOauthUrl() {
        if (StringUtils.isBlank(oauthUrl)) {
            return apiUrl;
        }
        return oauthUrl;
    }
}
