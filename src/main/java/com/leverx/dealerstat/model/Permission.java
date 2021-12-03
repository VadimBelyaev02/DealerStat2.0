package com.leverx.dealerstat.model;

//public enum Permission {
//    READ("read"),
//    WRITE("write");
//
//    public String getPermission() {
//        return permission;
//    }
//
//    private final String permission;
//
//    Permission(String permission) {
//        this.permission = permission;
//    }
//}

public enum Permission {
    TRADE("add_object"),
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
