package com.starsolns.todo.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.starsolns.todo.R
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.databinding.FragmentHomeBinding
import com.starsolns.todo.utils.hideKeyBoard
import com.starsolns.todo.view.actions.OnItemSwiped
import com.starsolns.todo.view.adapter.TodoAdapter
import com.starsolns.todo.viewmodel.MainViewModel
import com.starsolns.todo.viewmodel.SharedViewModel
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedVM: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        sharedVM = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //show data on recyclerview
        todoAdapter = TodoAdapter(requireContext())
        binding.listRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.listRv.adapter = todoAdapter

        //add recyclerview animator
        binding.listRv.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 1000

        }
        //swipe to delete item
        onSwipeItem(binding.listRv)

        viewModel.getAllTasks.observe(requireActivity(), Observer { todos ->
            //sharedVM.chckIfEmpty(todos)
            todoAdapter.setData(todos)
        })

        //hide keyboard
        hideKeyBoard(requireActivity())

//        sharedVM.isEmpty.observe(requireActivity(), Observer {
//            setVisibility(it)
//        })

        setHasOptionsMenu(true)

        return binding.root
    }

//    private fun setVisibility(isEmpty: Boolean) {
//        if(isEmpty){
//            binding.noTasks.visibility = View.VISIBLE
//            binding.emptyList.visibility = View.VISIBLE
//        }else{
//            binding.noTasks.visibility = View.INVISIBLE
//            binding.emptyList.visibility = View.INVISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu_options, menu)

        //implement search view
        val query = menu.findItem(R.id.nav_search)
        val searchView = query.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> deleteAll()
            R.id.priority_high -> viewModel.sortByHigh.observe(
                this, Observer { todoAdapter.setData(it) }
            )
            R.id.priority_low -> viewModel.sortByLow.observe(
                this, Observer {
                    todoAdapter.setData(it)
                }
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSwipeItem(recyclerView: RecyclerView) {
        val onSwipeCallback = object : OnItemSwiped() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = todoAdapter.todoList[viewHolder.adapterPosition]
                //delete item
                viewModel.delete(item)
                todoAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                //restore item
                onRestoreItem(viewHolder.itemView, item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(onSwipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun onRestoreItem(view: View, item: TodoData) {
        val snackBar =
            Snackbar.make(view, "Successfully deleted ${item.title}", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            viewModel.insert(item)
        }
        snackBar.show()
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete ALl")
        builder.setMessage("Are you sure you want to delete All?")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
            Toast.makeText(requireActivity(), "Deleted all items successfully", Toast.LENGTH_LONG)
                .show()

        }
        builder.setNegativeButton("No") { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            queryTheDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            queryTheDatabase(query)
        }
        return true
    }

    private fun queryTheDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.query(searchQuery).observe(this, Observer { res ->
            res?.let { list ->
                todoAdapter.setData(list)
            }
        })
    }
}