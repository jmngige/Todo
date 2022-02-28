package com.starsolns.todo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starsolns.todo.R
import com.starsolns.todo.data.model.Priority
import com.starsolns.todo.data.model.TodoData
import com.starsolns.todo.view.fragments.HomeFragmentDirections

class TodoAdapter(private val ctx: Context) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

     var todoList = emptyList<TodoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(ctx).inflate(R.layout.todo_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = todoList[position]
        holder.bindData(currentItem.title, currentItem.description)

        when (currentItem.priority) {
            Priority.HIGH -> {
                holder.cardOption.setBackgroundColor(ContextCompat.getColor(ctx, R.color.high))
            }
            Priority.MEDIUM -> {
                holder.cardOption.setBackgroundColor(ContextCompat.getColor(ctx, R.color.medium))
            }
            Priority.LOW -> {
                holder.cardOption.setBackgroundColor(ContextCompat.getColor(ctx, R.color.low))
            }
        }

        holder.layout_bg.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.todo_title)
        private val description: TextView = itemView.findViewById(R.id.todo_description)
         val cardOption: CardView = itemView.findViewById(R.id.todo_priority_indicator)
        val layout_bg: ConstraintLayout = itemView.findViewById(R.id.todo_layout_bg)

        fun bindData(mTitle: String, mDescription: String) {
            title.text = mTitle
            description.text = mDescription
        }
    }

    fun setData(dataList: List<TodoData>){
//        val todoDiffUtil = TodoDiffUtil(todoList, dataList)
//        val diffUtilResult = DiffUtil.calculateDiff(todoDiffUtil)
//        diffUtilResult.dispatchUpdatesTo(this)
        this.todoList = dataList
        notifyDataSetChanged()

    }
}