package com.example.demoproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.demoproject.util.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.UserTable.TABLE_NAME,
    indices = [Index(value = [DatabaseConstants.UserTable.COLUMN_USERNAME], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.UserTable.COLUMN_ID)
    val id: Long = 0,

    @ColumnInfo(name = DatabaseConstants.UserTable.COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = DatabaseConstants.UserTable.COLUMN_USERNAME)
    val username: String,

    @ColumnInfo(name = DatabaseConstants.UserTable.COLUMN_PASSWORD)
    val password: String,
)
