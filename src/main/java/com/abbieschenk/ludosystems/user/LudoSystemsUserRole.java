package com.abbieschenk.ludosystems.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration for {@link LudoSystemsUser} roles.
 *
 * @author abbie
 */
public enum LudoSystemsUserRole implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
