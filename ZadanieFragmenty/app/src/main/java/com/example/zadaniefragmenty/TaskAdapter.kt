package com.example.zadaniefragmenty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zadaniefragmenty.databinding.ListItemBinding

class ListAdapter(private val list: ArrayList<Task>)
    : RecyclerView.Adapter<ListAdapter.TaskViewHolder>() {

    class TaskViewHolder(var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = list[position]
        holder.binding.task = task
    }

    override fun getItemCount(): Int = list.size

}

