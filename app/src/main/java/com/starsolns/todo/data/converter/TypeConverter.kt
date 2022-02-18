package com.starsolns.todo.data.converter

import androidx.room.TypeConverter
import com.starsolns.todo.data.model.Priority

class TypeConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

}