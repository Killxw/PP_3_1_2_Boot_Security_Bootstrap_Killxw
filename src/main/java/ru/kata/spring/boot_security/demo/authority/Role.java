package ru.kata.spring.boot_security.demo.authority;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private final String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
