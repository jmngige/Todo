package com.starsolns.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.starsolns.todo.data.database.TodoDatabase
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository
    val getAllTasks: LiveData<List<TodoData>>
    val sortByHigh: LiveData<List<TodoData>>
    val sortByLow: LiveData<List<TodoData>>

    init {
         repository = TodoRepository(todoDao)
        getAllTasks = repository.getAllTasks
        sortByHigh = repository.sortByHigh
        sortByLow = repository.sortByLow
    }

    fun insert(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todoData)
        }
    }

    fun update(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todoData)
        }
    }

    fun delete(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(todoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun query(query: String): LiveData<List<TodoData>> {
      return repository.query(query)
    }
}