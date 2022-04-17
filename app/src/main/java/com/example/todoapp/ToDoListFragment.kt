package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {
    private lateinit var binding:FragmentToDoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentToDoListBinding.inflate(inflater,container,false)
        binding.fAB.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_newToDoFragment)
        }
        return binding.root
    }

}