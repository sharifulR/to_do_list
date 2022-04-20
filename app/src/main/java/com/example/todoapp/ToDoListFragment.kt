package com.example.todoapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.FragmentToDoListBinding
import com.example.todoapp.prefdata.LoginPreference
import com.example.todoapp.viewModels.TodoViewModel
import kotlinx.coroutines.launch

class ToDoListFragment : Fragment() {
    private lateinit var binding: FragmentToDoListBinding
    private lateinit var preference: LoginPreference
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todo_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_logout -> {
                lifecycle.coroutineScope.launch {
                    preference.setLoginStatus(status = false, 0L, requireContext())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListBinding.inflate(inflater, container, false)
        preference = LoginPreference(requireContext())
        val adapter = TodoAdapter{model, action ->

        }
        binding.toDoListRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.toDoListRV.adapter = adapter
        preference.isLoggedInFlow
            .asLiveData()
            .observe(viewLifecycleOwner){
                if (!it) {
                    findNavController().navigate(R.id.action_toDoListFragment_to_loginFragment)
                }
            }

        preference.userIdFlow.asLiveData()
            .observe(viewLifecycleOwner) {
                todoViewModel.getTodoByUserId(it).observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }


        binding.fAB.setOnClickListener {
            findNavController().navigate(R.id.action_toDoListFragment_to_newToDoFragment)
        }
        return binding.root
    }
}