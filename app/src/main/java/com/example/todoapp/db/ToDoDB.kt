package com.example.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.daos.ToDoDao
import com.example.todoapp.daos.UserDao
import com.example.todoapp.entities.ToDoModel
import com.example.todoapp.entities.UserModel

@Database(entities = [ToDoModel::class, UserModel::class], version = 1)
abstract class ToDoDB : RoomDatabase() {

    abstract fun toDoDao() : ToDoDao
    abstract fun userDao() : UserDao

    companion object {
        private var db: ToDoDB? = null
        fun getDB(context: Context) : ToDoDB {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    ToDoDB::class.java,
                    "todo_db"
                ).build()
                return db!!
            }
            return db!!
        }
    }
}