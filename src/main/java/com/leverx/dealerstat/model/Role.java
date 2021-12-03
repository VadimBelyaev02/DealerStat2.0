package com.leverx.dealerstat.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.TRADE, Permission.UPDATE)),
    TRADER(Set.of(Permission.READ, Permission.TRADE)),
    USER(Set.of(Permission.READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
