package com.starsolns.todo.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.starsolns.todo.data.model.Priority
import java.io.Serializable


@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String
): Serializable