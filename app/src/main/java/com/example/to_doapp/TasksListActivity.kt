package com.example.to_doapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.beust.klaxon.Klaxon
import com.example.to_doapp.ui.main.TaskFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import models.Priority
import models.Task
import utils.Database
import java.util.*
import kotlin.collections.ArrayList


class TasksListActivity : AppCompatActivity() {

    private lateinit var tasks: List<Task>

    private lateinit var tasksLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.tasksLayout = this.findViewById(R.id.tasks_layout)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            this.openTaskForm()
        }

        this.initialize()
    }

    override fun onResume() {
        super.onResume()

        this.initialize()
    }

    private fun initialize() {
        this.tasksLayout.removeAllViews()
        this.tasks = Database.getNotFinishedTasks()

        val fragmentManager: FragmentManager = this.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        this.tasks.forEachIndexed { index, task ->

            val taskFragment = TaskFragment()
            val bundle = Bundle()
            bundle.putParcelable("task", task)
            bundle.putInt("index", index)
            taskFragment.arguments = bundle

            fragmentTransaction.add(R.id.tasks_layout, taskFragment)
        }

        fragmentTransaction.commit()
    }

    private fun openTaskForm() {
        val intent = Intent(this, TaskFormActivity::class.java).apply { }
        startActivity(intent)
    }
}