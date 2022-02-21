package com.starsolns.todo.view.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.starsolns.todo.R
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.databinding.FragmentAddBinding
import com.starsolns.todo.viewmodel.MainViewModel
import com.starsolns.todo.viewmodel.SharedViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var sharedVM: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedVM = ViewModelProvider(this)[SharedViewModel::class.java]
        setHasOptionsMenu(true)

        binding.addSpinnerOptions.onItemSelectedListener = sharedVM.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_task) {
            saveTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTask() {
        var title = binding.addTitle.text.toString()
        var priority = binding.addSpinnerOptions.selectedItem.toString()
        var description = binding.addDescription.text.toString()

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            val snack = view?.let {
                Snackbar.make(
                    it,
                    "Please fill all empty spaces first",
                    Snackbar.LENGTH_LONG
                )
            }
            snack!!.show()
        } else {

            val task = TodoData(0, title, sharedVM.parsePriority(priority), description)
            viewModel.insert(task)
            Toast.makeText(requireContext(), "Task added successfully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }
    }
}