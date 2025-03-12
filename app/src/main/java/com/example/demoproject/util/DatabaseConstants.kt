package com.example.demoproject.util

object DatabaseConstants {

    object ProductTable {
        const val TABLE_NAME = "product_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_PRICE = "price"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_IS_SELECTED = "is_selected"
    }

    object UserTable {
        const val TABLE_NAME = "user_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }
}
