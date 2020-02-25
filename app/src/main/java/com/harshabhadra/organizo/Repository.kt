package com.harshabhadra.organizo

import android.app.Application
import androidx.lifecycle.LiveData
import com.harshabhadra.organizo.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private val taskDao: TaskDao
    private val categoryDao: CategoryDao
    private val userTaskList: LiveData<List<Task>>

    private var categoryList: LiveData<List<Category>>

    constructor(application: Application) {
        val organizoDatabae: OrganizoDatabae = OrganizoDatabae.getInstance(application)
        taskDao = organizoDatabae.TaskDao
        categoryDao = organizoDatabae.categoryDao
        userTaskList = taskDao.getAllTasks()
        categoryList = categoryDao.getCategories()
    }

    //Get All tasks
    fun getAllTasks(): LiveData<List<Task>> {
        return userTaskList
    }

    //Get all categories
    fun getCategorieList(): LiveData<List<Category>> {
        return categoryList
    }

    //Insert a task
    suspend fun insertTask(task: Task) {
        withContext(Dispatchers.IO){
            taskDao.insertTask(task)
        }
    }

    //Insert a category
    suspend fun insertCategory(category: Category) {

        withContext(Dispatchers.IO){
            categoryDao.insertCategory(category)
        }
    }
}