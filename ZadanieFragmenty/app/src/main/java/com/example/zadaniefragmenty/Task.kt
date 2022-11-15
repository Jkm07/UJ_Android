package com.example.zadaniefragmenty

import androidx.lifecycle.ViewModel

data class Task(
    val name: String,
    val desc: String,
    val date: String,
    val status: Boolean,
)

class TaskViewModel() : ViewModel() {
    private var _taskList = ArrayList<Task>()
    fun addTask(task: Task) {
        _taskList.add(task);
    }

    fun setTask(taskList: ArrayList<Task>) {
        _taskList = taskList
    }

    fun getSize() : Int {
        return _taskList.size;
    }

    fun getTask(id: Int) : Task {
        return _taskList[id]
    }
}