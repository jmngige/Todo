package com.starsolns.todo.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.starsolns.todo.R
import com.starsolns.todo.databinding.FragmentHomeBinding
import com.starsolns.todo.view.adapter.TodoAdapter
import com.starsolns.todo.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]



        //show data on recyclerview
        todoAdapter = TodoAdapter(requireContext())
        binding.listRv.layoutManager = LinearLayoutManager(requireContext())
        binding.listRv.adapter = todoAdapter

        viewModel.getAllTasks.observe(requireActivity(), Observer { todos->
            todoAdapter.setData(todos)
        })

        binding.fab.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
            findNavController().navigate(action)
        }

        binding.homeLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_updateFragment)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu_options, menu)
    }
}