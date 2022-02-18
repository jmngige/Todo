package com.starsolns.todo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.data.converter.TypeConverter

@Database(entities = [TodoData::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private var instance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instanced = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                instance = instanced
                return instance as TodoDatabase
            }
        }
    }

}