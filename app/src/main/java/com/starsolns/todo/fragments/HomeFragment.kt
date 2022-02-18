package com.starsolns.todo.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.starsolns.todo.R
import com.starsolns.todo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

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