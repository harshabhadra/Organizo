package com.harshabhadra.organizo.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface taskDao {

    @Insert
    fun insertTask(task:Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM user_tasks_table WHERE taskId = :id")
    fun getSingleTask(id: Long):Task?

    @Query("SELECT * FROM user_tasks_table ORDER BY taskId DESC")
    fun getAllTasks():LiveData<List<Task>>

    @Query("DELETE FROM user_tasks_table")
    fun deleteAllTask()

    @Delete
    fun deleteSingleTask(id: Long)
}