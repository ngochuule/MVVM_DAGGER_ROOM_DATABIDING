package com.example.mvvmApp.data.datasource.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "table1",
    indices = arrayOf(
        Index(value = ["table1_id"], unique = true)
    )
)
data class Table1App(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "table1_id")
    var tableId: String,
    @ColumnInfo(name = "table1_name")
    var tableName: String
)
