package com.example.to_doapp.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.JsonReader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import com.example.to_doapp.R
import kotlinx.android.synthetic.main.task_fragment.*
import kotlinx.android.synthetic.main.task_fragment.view.*
import models.Priority
import models.Task
import utils.Database
import utils.DateUtils

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private var viewModel: Task? = null

    private lateinit var layout: LinearLayout
    private lateinit var nameText: TextView
    private lateinit var datesText: TextView
    private lateinit var completedCheckBox: CheckBox
    private lateinit var priorityBorder: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.layout = inflater.inflate(R.layout.task_fragment, container, false) as LinearLayout

        this.nameText = this.layout.findViewById(R.id.name)
        this.datesText = this.layout.findViewById(R.id.date_range)
        this.completedCheckBox = this.layout.findViewById(R.id.completed_check_box)
        this.priorityBorder = this.layout.findViewById(R.id.border)

        this.viewModel = this.arguments?.getParcelable("task")

        if (this.viewModel != null) {
            val copy = this.viewModel

            if (copy != null) {
                this.setData(copy)
            }
        }

        this.completedCheckBox.setOnClickListener() {
            Database.toggleTask(this.viewModel?.id, (it as CheckBox).isChecked)
        }

        return this.layout
    }

    private fun setData(task: Task) {
        this.nameText.text = task.name

        var datesText: String = ""

        if (task.start != null) {
            datesText += "от " + DateUtils.stringifyDateTime(task.start) + " "
        }

        if (task.end != null) {
            datesText += "до " + DateUtils.stringifyDateTime((task.end))
        }

        this.datesText.text = datesText
        this.completedCheckBox.isChecked = task.isComplete

        when (task.priority) {
            Priority.Low -> {
                this.priorityBorder.setBackgroundColor(Color.GREEN)
            }
            Priority.Medium -> {
                this.priorityBorder.setBackgroundColor(Color.YELLOW)
            }
            Priority.High -> {
                this.priorityBorder.setBackgroundColor(Color.RED)
            }
            else -> {
                this.priorityBorder.setBackgroundColor(Color.GRAY)
            }
        }
    }
}