package com.example.mvvmApp.data.datasource.local

import androidx.room.*
import com.example.mvvmApp.data.datasource.db.Table1App

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIfNotExists(table1: Table1App): Long

    @Query("SELECT * FROM table1")
    fun getAll(): List<Table1App>

    @Query("SELECT * FROM table1 WHERE id = :tableId")
    fun getById(tableId: String): Table1App?

    @Delete
    fun delete(table: Table1App): Int
}