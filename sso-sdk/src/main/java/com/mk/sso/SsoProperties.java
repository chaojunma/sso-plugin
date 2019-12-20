package com.mk.sso;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {

    //是否启用
    private boolean enable;

    //放行的url
    private  String[] ignoreUrls = {};

    // sso服务器地址
    private String ssoServer;

}
