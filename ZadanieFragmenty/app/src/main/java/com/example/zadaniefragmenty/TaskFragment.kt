package com.example.zadaniefragmenty

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

class TaskFragment(private val _id: Int) : Fragment() {
    private val taskList: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(arrayListOf(taskList.getTask(_id)))
        }
        val me = this;
        val swipeToDelete = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                getActivity()?.getSupportFragmentManager()?.beginTransaction()?.remove(me)?.commit();
                recyclerView.adapter?.notifyItemRemoved(position);
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}