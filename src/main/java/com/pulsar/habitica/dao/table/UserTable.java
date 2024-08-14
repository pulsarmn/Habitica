package com.pulsar.habitica.dao.table;

public final class UserTable {

    public static final String SCHEMA_NAME = "users";
    public static final String TABLE_NAME = "users";
    public static final String FULL_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;

    public static final String ID_COLUMN = "id";
    public static final String EMAIL_COLUMN = "email";
    public static final String PASSWORD_COLUMN = "password";
    public static final String NICKNAME_COLUMN = "nickname";
    public static final String CREATED_AT_COLUMN = "created_at";

    private UserTable() {}
}
