package com.starsolns.todo.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.starsolns.todo.R
import com.starsolns.todo.databinding.FragmentAddBinding
import com.starsolns.todo.viewmodel.MainViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? =null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_task){
            saveTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTask() {
        var title = binding.addTitle.text.toString()
        var priority = binding.addSpinnerOptions.selectedItem.toString()
        var description = binding.addDescription.text.toString()

        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            val snack = view?.let { Snackbar.make(it,"Please fill all empty spaces first",Snackbar.LENGTH_LONG) }
            snack!!.show()
        }
    }
}