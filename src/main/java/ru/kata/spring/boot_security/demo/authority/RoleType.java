package ru.kata.spring.boot_security.demo.authority;

public enum RoleType {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return name();
    }
}
