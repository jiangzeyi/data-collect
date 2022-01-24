package com.ivw.task.enums;

public enum DBType {

    /**
     * Mysql
     */
    MYSQL(0),

    /**
     * Oracle
     */
    ORACLE(1);

    private final int value;

    DBType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
