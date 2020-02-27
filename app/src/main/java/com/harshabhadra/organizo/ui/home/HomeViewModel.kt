package com.harshabhadra.organizo.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.harshabhadra.organizo.Repository
import com.harshabhadra.organizo.database.Category
import com.harshabhadra.organizo.database.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private lateinit var auth: FirebaseAuth

class HomeViewModel(private val application: Application) : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val homeViewModleJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + homeViewModleJob)

    //Store current user
    private var _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    //Store current date
    private var _today = MutableLiveData<String>()
    val today: LiveData<String>
        get() = _today

    private val organizoRepository: Repository = Repository(application = application)

    //Store list of task
    val taskList: LiveData<List<Task>>

    //Store list of categories
    val categoryList: LiveData<List<Category>>

    init {
        categoryList = organizoRepository.getCategorieList()
        taskList = organizoRepository.getAllTasks()
    }

    //Function to get currentUser
    fun getCurrentUser() {
        uiScope.launch {
            _currentUser.value = auth.currentUser
            _userName.value = _currentUser.value?.displayName!!.split(" ")[0]
        }
    }

    //Get today
    fun getToday(){
        val date = Calendar.getInstance().time
        _today.value = date.toString("E, dd MMM yyyy")
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    override fun onCleared() {
        super.onCleared()
        homeViewModleJob.cancel()
    }
}