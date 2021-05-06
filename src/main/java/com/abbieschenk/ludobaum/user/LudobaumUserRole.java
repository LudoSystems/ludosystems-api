package com.abbieschenk.ludobaum.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration for {@link LudobaumUser} roles.
 *
 * @author abbie
 */
public enum LudobaumUserRole implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
