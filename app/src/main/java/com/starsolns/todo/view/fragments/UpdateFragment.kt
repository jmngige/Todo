package com.starsolns.todo.view.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.starsolns.todo.R
import com.starsolns.todo.data.model.Priority
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.databinding.FragmentUpdateBinding
import com.starsolns.todo.viewmodel.MainViewModel
import com.starsolns.todo.viewmodel.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var shared_VM: SharedViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        
        shared_VM = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.update_todo-> updateTodo()
           R.id.delete_item -> deleteTodo()
       }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTodo() {
        var builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete ${args.todoUpdate.title}")
        builder.setMessage("Are you sure you want to delete ${args.todoUpdate.title}?")
        builder.setPositiveButton("Yes"){_,_->
            viewModel.delete(args.todoUpdate)
            Toast.makeText(requireActivity(), "Post deleted successfully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
        builder.setNegativeButton("No"){_,_->

        }
        val dialog = builder.create()
        dialog.show()
        }

    private fun updateTodo() {
        var title = binding.updateTitle.text.toString()
        var description = binding.updateDescription.text.toString()
        var priority = binding.updateSpinnerOptions.selectedItem.toString()

        val parsedPr = shared_VM.parsePriority(priority)
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            Toast.makeText(requireActivity(), "Please fill all the blank spaces first", Toast.LENGTH_LONG).show()
        }else {
            val updatedTodo = TodoData(
                args.todoUpdate.id,
                title,
                parsedPr,
                description
            )

            viewModel.update(updatedTodo)
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            Toast.makeText(requireActivity(), "Task updated successfully", Toast.LENGTH_LONG).show()
        }
        
    }

    private fun parsePriority(priority: Priority): Int{
        return when(priority){
            Priority.HIGH->0
            Priority.MEDIUM->1
            Priority.LOW->2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}