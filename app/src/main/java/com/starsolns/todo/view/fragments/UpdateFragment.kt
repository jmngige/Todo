package com.starsolns.todo.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.starsolns.todo.R
import com.starsolns.todo.data.model.Priority
import com.starsolns.todo.databinding.FragmentUpdateBinding
import com.starsolns.todo.viewmodel.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var shared_VM: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        shared_VM = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding.updateTitle.setText(args.todoUpdate.title)
        binding.updateDescription.setText(args.todoUpdate.description)
        binding.updateSpinnerOptions.setSelection(parsePriority(args.todoUpdate.priority))
        binding.updateSpinnerOptions.onItemSelectedListener = shared_VM.listener


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu_options, menu)
    }

    private fun parsePriority(priority: Priority): Int{
        return when(priority){
            Priority.HIGH->0
            Priority.MEDIUM->1
            Priority.LOW->2
        }
    }
}