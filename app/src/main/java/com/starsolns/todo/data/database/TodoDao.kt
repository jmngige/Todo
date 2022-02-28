package com.starsolns.todo.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.starsolns.todo.data.model.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks() : LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoData: TodoData)

    @Update
    suspend fun update(todoData: TodoData)

    @Delete
    suspend fun delete(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :myQuery")
    fun query(myQuery: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<TodoData>>


}