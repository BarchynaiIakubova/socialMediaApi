package com.barchynai.socialMediaApi.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    SUPER_ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
