package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.todoapp.databinding.FragmentNewToDoBinding
import com.example.todoapp.dialogs.DatePickerDialogFragment
import com.example.todoapp.dialogs.TimePickerDialogFragment
import com.example.todoapp.utils.getFormatedDateTime
import com.example.todoapp.utils.priority_normal


class NewToDoFragment : Fragment() {
    private lateinit var binding: FragmentNewToDoBinding
    var priority = priority_normal
    var dateInMillis = System.currentTimeMillis()
    var timeInMillis = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewToDoBinding.inflate(inflater, container, false)
        binding.priorityRG.setOnCheckedChangeListener { group, checkedId ->
            val rb: RadioButton = group.findViewById(checkedId)
            priority = rb.text.toString()
        }
        binding.dateBtn.setOnClickListener {
            DatePickerDialogFragment { timestamp ->
                dateInMillis = timestamp
                binding.showDateTV.text = getFormatedDateTime(timestamp, "dd/MM/yyyy")
            }.show(childFragmentManager, "date_picker")

        }
        binding.timeBtn.setOnClickListener {
            TimePickerDialogFragment{it ->
                timeInMillis = it
                binding.showTimeTV.text = getFormatedDateTime(it, "hh:mm a")
            }.show(childFragmentManager, "time_picker")
        }

        return binding.root
    }

}