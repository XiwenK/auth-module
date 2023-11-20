package com.manunin.score.model;

public enum ERole {
    ADMIN("ADMIN"),
    USER("User role");

    final String name;

    ERole(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
