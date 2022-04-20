package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoRowBinding
import com.example.todoapp.entities.ToDoModel
import com.example.todoapp.utils.todo_delete
import com.example.todoapp.utils.todo_edit

class TodoAdapter(val actionCallback: (ToDoModel, String) -> Unit) : ListAdapter<ToDoModel, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    class TodoViewHolder(
        private val binding: TodoRowBinding,
        val actionCallback: (ToDoModel, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoModel: ToDoModel) {
            binding.todo = todoModel
            binding.rowCompleteCB.setOnClickListener {
                actionCallback(todoModel, todo_edit)
            }

            binding.rowMenu.setOnClickListener {
                val popupMenu = PopupMenu(it.context, it)
                val inflater = popupMenu.menuInflater
                inflater.inflate(R.menu.row_item_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.item_delete -> {
                            actionCallback(todoModel, todo_delete)
                            true
                        }
                        R.id.item_edit -> {
                            actionCallback(todoModel, todo_edit)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, actionCallback)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoModel = getItem(position)
        holder.bind(todoModel)
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<ToDoModel>() {
    override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem.todoId == newItem.todoId
    }

    override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem == newItem
    }

}