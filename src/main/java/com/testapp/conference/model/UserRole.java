package com.testapp.conference.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER_ROLE, ADMIN_ROLE;

    public String getAuthority() {
        return name();
    }

}
