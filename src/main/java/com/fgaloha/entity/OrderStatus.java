package com.fgaloha.entity;

public enum OrderStatus {
	PENDING("注文受付中"),
    SHIPPED("発送済み"),
    DELIVERED("配達完了"),
    CANCELLED("キャンセル");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
