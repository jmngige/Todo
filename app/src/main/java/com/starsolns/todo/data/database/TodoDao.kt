package com.starsolns.todo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.starsolns.todo.data.model.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks() : LiveData<List<TodoData>>

    @Insert
    suspend fun insert(todoData: TodoData)
}