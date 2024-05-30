package com.sparta.todoproject.entity;

public enum UserRoleEnum {
    USER("ROLE_USER"),
    ADMIN("ROLE_USER");
    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}
