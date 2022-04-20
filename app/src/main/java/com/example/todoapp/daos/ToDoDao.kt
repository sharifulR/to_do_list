package com.example.todoapp.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.entities.ToDoModel

@Dao
interface ToDoDao {
    @Insert
    suspend fun insertTodo(todoModel: ToDoModel)

    @Update
    suspend fun updateTodo(todoModel: ToDoModel)

    @Delete
    suspend fun deleteTodo(todoModel: ToDoModel)

    @Query("select * from tbl_todo where user_id = :userId")
    fun getTodosByUserId(userId: Long) : LiveData<List<ToDoModel>>
}