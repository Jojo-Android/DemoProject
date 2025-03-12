package com.example.demoproject.data.model

import androidx.room.*
import com.example.demoproject.util.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.ProductTable.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [DatabaseConstants.UserTable.COLUMN_ID],
            childColumns = [DatabaseConstants.ProductTable.COLUMN_USER_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = [DatabaseConstants.ProductTable.COLUMN_USER_ID])]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_ID)
    val id: Long = 0,

    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_PRICE)
    val price: Double,

    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_IMAGE)
    val image: String,

    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_USER_ID)
    val userId: Long,

    @ColumnInfo(name = DatabaseConstants.ProductTable.COLUMN_IS_SELECTED)
    var isSelected: Boolean = false,
)
