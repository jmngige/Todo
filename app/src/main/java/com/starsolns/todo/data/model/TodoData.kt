package com.starsolns.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.starsolns.todo.data.model.Priority

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
     private val id: Int,
    private val title: String,
    private val priority: Priority,
    private val description: String

)