package com.harshabhadra.organizo.ui.userTask

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AddTaskViewModel(private val context: Application) : AndroidViewModel(context) {

    //Current Date String
    private var _dateString = MutableLiveData<String>()
    val dateString: LiveData<String>
        get() = _dateString

    //Current Time String
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

    //Get Current Time
    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    //Format Date
     fun changeDateFormat(day:Int, month:Int, year:Int):String{

        val calender = Calendar.getInstance()
        calender.set(year,month,day)
        val d = calender.time
        _dateString.value = d.toString("E, dd MMM yyyy")
        return d.toString("E, dd MMM yyyy")
    }

    fun changeTimeFormat(hour:Int, min:Int):String{
        val date = SimpleDateFormat("HH:mm", Locale.getDefault()).parse("$hour:$min")
        _timeString.value = date.toString("hh:mm aa")
        return date.toString("hh:mm aa")
    }
}