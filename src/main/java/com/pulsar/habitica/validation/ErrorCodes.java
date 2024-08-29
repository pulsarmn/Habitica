package com.pulsar.habitica.validation;

public final class ErrorCodes {

    public static final String NICKNAME_REQUIRED = "nickname.required";
    public static final String NICKNAME_TAKEN = "nickname.already.taken";
    public static final String LONG_NICKNAME = "nickname.too.long";
    public static final String INVALID_NICKNAME = "nickname.invalid";

    public static final String EMAIL_REQUIRED = "email.required";
    public static final String EMAIL_TAKEN = "email.already.taken";
    public static final String INVALID_EMAIL = "email.invalid";

    public static final String PASSWORD_REQUIRED = "password.required";
    public static final String LONG_PASSWORD = "password.too.long";
    public static final String SHORT_PASSWORD = "password.too.short";
    public static final String INVALID_PASSWORD = "password.invalid";
    public static final String DONT_MATCH = "password.dont.match";

    public static final String IDENTIFIER_REQUIRED = "identifier.required";

    private ErrorCodes() {}
}
