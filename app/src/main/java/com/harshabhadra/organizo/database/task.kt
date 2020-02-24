package com.harshabhadra.organizo.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tasks_table")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val taskId:Long,

    @NonNull
    @ColumnInfo(name = "task_name")
    var taskName:String,

    @ColumnInfo(name = "task_category")
    var taskCategory:String = "Regular",

    @ColumnInfo(name = "task_description")
    var taskDescription:String,

    @NonNull
    @ColumnInfo(name = "task_start_time")
    var startTime:String,

    @ColumnInfo(name = "task_end_time")
    var endTime:String,

    @NonNull
    @ColumnInfo(name = "task_start_date")
    var startDate:String,

    @ColumnInfo(name = "task_end_date")
    var endDate:String
)