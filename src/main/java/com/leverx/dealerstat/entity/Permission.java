package com.leverx.dealerstat.entity;

public enum Permission {
    TRADE("trade"),
    DELETE("delete"),
    UPDATE("update"),
    READ("read"),
    WRITE("write");


    public String getPermission() {
        return permission;
    }

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
