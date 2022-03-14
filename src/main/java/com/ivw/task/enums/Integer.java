package com.ivw.task.enums;

public enum Integer {

    /**
     * Mysql
     */
    MYSQL(0),

    /**
     * Oracle
     */
    ORACLE(1);

    private final int value;

    Integer(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
