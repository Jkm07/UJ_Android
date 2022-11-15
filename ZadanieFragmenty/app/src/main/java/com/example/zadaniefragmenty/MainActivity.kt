package com.example.zadaniefragmenty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    private val taskList: TaskViewModel by viewModels()
    private lateinit var mydatabase: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mydatabase = DataBaseHelper(this)
        taskList.setTask(mydatabase.getAll())

        val fm:FragmentManager = supportFragmentManager;
        repeat(taskList.getSize()) { it ->
            fm.beginTransaction().add(R.id.container, TaskFragment(it)).commit();
        }
    }
}