package com.harshabhadra.organizo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(

    @PrimaryKey
    @ColumnInfo(name = "category_name")
    var categoryName:String
)