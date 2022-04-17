package com.example.todoapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tbl_todo")
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    val todoId:Long=0,
    @ColumnInfo(name="user_id")
    val userId:Long,
    val name:String,
    val priority: String,
    val date:Long=System.currentTimeMillis(),
    val time:Long,
    val completed: Boolean=false
)
