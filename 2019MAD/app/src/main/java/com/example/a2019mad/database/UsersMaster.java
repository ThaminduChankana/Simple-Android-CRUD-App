package com.example.a2019mad.database;

import android.provider.BaseColumns;

public class UsersMaster {

    private UsersMaster(){}

    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_DOB = "dob";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_GENDER = "gender";
    }

}
