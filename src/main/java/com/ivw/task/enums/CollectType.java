package com.ivw.task.enums;

public enum CollectType {

    /**
     * SQL
     */
    SQL(0),

    /**
     * API
     */
    API(1);

    private final int value;

    CollectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
