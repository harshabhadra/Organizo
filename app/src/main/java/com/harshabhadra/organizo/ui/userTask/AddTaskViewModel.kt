package com.harshabhadra.organizo.ui.userTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshabhadra.organizo.Repository
import com.harshabhadra.organizo.database.Category
import com.harshabhadra.organizo.database.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddTaskViewModel(private val context: Application) : AndroidViewModel(context) {

    //Initializing Corotiunes job
    private val addTaskViewModelJOb = Job()

    //Initialize scope
    private val uiScope = CoroutineScope(Dispatchers.Main + addTaskViewModelJOb)

    //Declaring repository
    private val organizoRepository: Repository = Repository(context)

    private var _taskInserted = MutableLiveData<Boolean>()
    val taskInserted: LiveData<Boolean>
        get() = _taskInserted

    val allCategories: LiveData<List<Category>>

    init {
        allCategories = organizoRepository.getCategorieList()
        _taskInserted.value = false
    }

    //Insert a category to database
    fun insertCategory(category: Category) {
        uiScope.launch {
            organizoRepository.insertCategory(category)
        }
    }

    //Insert a task
    fun insertTask(task: Task) {
        uiScope.launch {
            organizoRepository.insertTask(task)
            _taskInserted.value = true
        }
    }

    fun taskInserTionCompleted(){
        _taskInserted.value = false
    }

    //Current Date String
    private var _dateString = MutableLiveData<String>()
    val dateString: LiveData<String>
        get() = _dateString

    //Current Time String
    private var _timeString = MutableLiveData<String>()
    val timeString: LiveData<String>
        get() = _timeString

    //Get Current Time
    fun getTime() {
        val date = getCurrentDateTime()
        _timeString.value = date.toString("hh:mm aa", Locale.getDefault())
    }

    //Get current Date
    fun getDate() {
        val date = getCurrentDateTime()
        _dateString.value = date.toString("E, dd MMM yyyy")
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    //Get Current Time
    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    //Format Date
    fun changeDateFormat(day: Int, month: Int, year: Int): String {

        val calender = Calendar.getInstance()
        calender.set(year, month, day)
        val d = calender.time
        _dateString.value = d.toString("E, dd MMM yyyy")
        return d.toString("E, dd MMM yyyy")
    }

    //Change Time format
    fun changeTimeFormat(hour: Int, min: Int): String {
        val date = SimpleDateFormat("HH:mm", Locale.getDefault()).parse("$hour:$min")
        _timeString.value = date.toString("hh:mm aa")
        return date.toString("hh:mm aa")
    }

    override fun onCleared() {
        super.onCleared()
        addTaskViewModelJOb.cancel()
    }
}