package com.example.todoapp.repository

import com.example.todoapp.daos.ToDoDao
import com.example.todoapp.entities.ToDoModel

class TodoRepository(val todoDao: ToDoDao) {

    suspend fun insertTodo(todoModel: ToDoModel) {
        todoDao.insertTodo(todoModel)
    }

    fun getTodoByUserId(userId: Long) = todoDao.getTodosByUserId(userId)
}