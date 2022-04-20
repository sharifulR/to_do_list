package com.example.todoapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.db.ToDoDB
import com.example.todoapp.entities.ToDoModel
import com.example.todoapp.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val toDoDao = ToDoDB.getDB(application).toDoDao()
    private val repository = TodoRepository(toDoDao)
    fun insertTodo(todoModel: ToDoModel) {
        viewModelScope.launch {
            repository.insertTodo(todoModel)
        }
    }

    fun getTodoByUserId(userId: Long) = repository.getTodoByUserId(userId)
}