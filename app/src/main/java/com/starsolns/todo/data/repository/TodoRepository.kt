package com.starsolns.todo.data.repository

import androidx.lifecycle.LiveData
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.data.database.TodoDao

class TodoRepository(private val todoDao: TodoDao) {

    val getAllTasks: LiveData<List<TodoData>> = todoDao.getAllTasks()

    suspend fun insert(todoData: TodoData){
        todoDao.insert(todoData)
    }


}