package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentNewToDoBinding
import com.example.todoapp.dialogs.DatePickerDialogFragment
import com.example.todoapp.dialogs.TimePickerDialogFragment
import com.example.todoapp.entities.ToDoModel
import com.example.todoapp.prefdata.LoginPreference
import com.example.todoapp.utils.getFormatedDateTime
import com.example.todoapp.utils.priority_normal
import com.example.todoapp.viewModels.TodoViewModel
import java.util.*


class NewToDoFragment : Fragment() {
    private lateinit var binding: FragmentNewToDoBinding
    private val todoViewModel: TodoViewModel by viewModels()
    private lateinit var preference: LoginPreference
    var priority = priority_normal
    var dateInMillis = System.currentTimeMillis()
    var timeInMillis = System.currentTimeMillis()
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0
    private var userId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDays()
        preference = LoginPreference(requireContext())
        preference.userIdFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                userId = it
            }
        binding = FragmentNewToDoBinding.inflate(inflater, container, false)
        binding.priorityRG.setOnCheckedChangeListener { group, checkedId ->
            val rb: RadioButton = group.findViewById(checkedId)
            priority = rb.text.toString()
        }
        binding.dateBtn.setOnClickListener {
            DatePickerDialogFragment { day, month, year, timestamp ->
                dateInMillis = timestamp
                this.day = day
                this.month = month
                this.year = year
                binding.showDateTV.text = getFormatedDateTime(timestamp, "dd/MM/yyyy")
            }.show(childFragmentManager, "date_picker")

        }
        binding.timeBtn.setOnClickListener {
            TimePickerDialogFragment{hour, minute, it ->
                timeInMillis = it
                this.hour = hour
                this.minute = minute
                binding.showTimeTV.text = getFormatedDateTime(it, "hh:mm a")
            }.show(childFragmentManager, "time_picker")
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.toDoNameET.text.toString()
            val todo = ToDoModel(
                name = name,
                userId = userId,
                priority = priority,
                date = dateInMillis,
                time = timeInMillis,
                day = day,
                month = month,
                year = year,
                hour = hour,
                minute = minute
            )
            todoViewModel.insertTodo(todo)
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun initDays() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
    }

}