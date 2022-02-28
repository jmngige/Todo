package com.starsolns.todo.view.adapter

import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.starsolns.todo.view.fragments.HomeFragmentDirections

class BindingAdapters {

    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(fab: FloatingActionButton, navigate: Boolean){
           fab.setOnClickListener {
               if (navigate){
                   val action = HomeFragmentDirections.actionHomeFragmentToAddFragment()
                   fab.findNavController().navigate(action)
               }
           }
        }

    }

}