package com.harshabhadra.organizo.ui.userTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*


class AddTaskViewModel(private val context: Application) : AndroidViewModel(context) {


    private var _dateString = MutableLiveData<String>()
    val dateString: LiveData<String>
        get() = _dateString

    private var _timeString = MutableLiveData<String>()
    val timeString: LiveData<String>
        get() = _timeString

    //Get Current Time
    fun getTime(){
        val date = getCurrentDateTime()
        _timeString.value = date.toString("hh:mm aa",Locale.getDefault())
    }

    //Get current Date
    fun getDate(){
        val date = getCurrentDateTime()
        _dateString.value = date.toString("E, dd MMM yyyy")
    }
    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}