package com.starsolns.todo.data.repository

import androidx.lifecycle.LiveData
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.data.database.TodoDao

class TodoRepository(private val todoDao: TodoDao) {

    val getAllTasks: LiveData<List<TodoData>> = todoDao.getAllTasks()
    val sortByHigh: LiveData<List<TodoData>> = todoDao.sortByHighPriority()
    val sortByLow: LiveData<List<TodoData>> = todoDao.sortByLowPriority()

    suspend fun insert(todoData: TodoData){
        todoDao.insert(todoData)
    }

    suspend fun update(todoData: TodoData){
        todoDao.update(todoData)
    }

    suspend fun delete(todoData: TodoData){
        todoDao.delete(todoData)
    }

    suspend fun deleteAll(){
        todoDao.deleteAll()
    }

    fun query(query: String): LiveData<List<TodoData>>{
        return todoDao.query(query)
    }
}