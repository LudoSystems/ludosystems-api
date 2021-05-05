package com.abbieschenk.ludobaum.user;

import org.springframework.security.core.GrantedAuthority;

public enum LudobaumUserRole implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
