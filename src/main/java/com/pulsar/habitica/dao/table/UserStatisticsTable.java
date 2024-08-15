package com.pulsar.habitica.dao.table;

public final class UserStatisticsTable {

    public static final String SCHEMA_NAME = "users";
    public static final String TABLE_NAME = "user_statistics";
    public static final String FULL_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;

    public static final String USER_ID = "user_id";
    public static final String TOTAL_VISITS = "total_visits";

    private UserStatisticsTable() {}
}
