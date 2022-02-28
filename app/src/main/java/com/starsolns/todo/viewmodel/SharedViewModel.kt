package com.starsolns.todo.viewmodel

import android.app.Application
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.starsolns.todo.R
import com.starsolns.todo.data.model.Priority
import com.starsolns.todo.data.model.TodoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val isEmpty: MutableLiveData<Boolean> = MutableLiveData(true)

    fun chckIfEmpty(todoData: List<TodoData>){
        isEmpty.value = todoData.isEmpty()
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            "Low Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.high))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.medium))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.low))}
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }

}