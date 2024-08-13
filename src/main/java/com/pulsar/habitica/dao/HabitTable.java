package com.pulsar.habitica.dao;

public final class HabitTable {

    public static final String SCHEMA_NAME = "task";
    public static final String TABLE_NAME = "habit";
    public static final String FULL_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;

    public static final String ID_COLUMN = "id";
    public static final String HEADING_COLUMN = "heading";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String COMPLEXITY_COLUMN = "complexity";
    public static final String BAD_SERIES_COLUMN = "bad_series";
    public static final String GOOD_SERIES_COLUMN = "good_series";
    public static final String USER_ID_COLUMN ="user_id";

    private HabitTable() {}
}
