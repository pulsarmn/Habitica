package com.pulsar.habitica.dao.table;

public final class UserImageTable {

    public static final String SCHEMA_NAME = "users";
    public static final String TABLE_NAME = "user_images";
    public static final String FULL_TABLE_NAME = SCHEMA_NAME + "." + TABLE_NAME;

    public static final String USER_ID = "user_id";
    public static final String IMAGE_ADDRESS = "image_addr";

    private UserImageTable() {}
}
