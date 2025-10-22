package com.anfony.usersapi.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

    private String aesSecret;

    public String getAesSecret() { return aesSecret; }
    public void setAesSecret(String aesSecret) { this.aesSecret = aesSecret; }
}
