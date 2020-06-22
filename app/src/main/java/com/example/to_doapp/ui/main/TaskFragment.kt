package com.example.to_doapp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.to_doapp.R
import models.Priority
import models.Task
import utils.Database
import utils.DateUtils


class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private var viewModel: Task? = null
    private var index: Int? = null

    private lateinit var layout: LinearLayout
    private lateinit var contentLayout: LinearLayout
    private lateinit var nameText: TextView
    private lateinit var datesText: TextView
    private lateinit var completedCheckBox: CheckBox
    private lateinit var priorityBorder: View

    private val deleteText: String = "Изтрий"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.layout = inflater.inflate(R.layout.task_fragment, container, false) as LinearLayout

        this.contentLayout = this.layout.findViewById(R.id.task_content)
        this.nameText = this.layout.findViewById(R.id.name)
        this.datesText = this.layout.findViewById(R.id.date_range)
        this.completedCheckBox = this.layout.findViewById(R.id.completed_check_box)
        this.priorityBorder = this.layout.findViewById(R.id.border)

        this.viewModel = this.arguments?.getParcelable("task")
        this.index = this.arguments?.getInt("index")

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

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val index = this.index

        if (index != null) {
            menu.add(index, index, 0, deleteText)
        }
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
                this.priorityBorder.setBackgroundResource(R.color.colorSuccess)
            }
            Priority.Medium -> {
                this.priorityBorder.setBackgroundResource(R.color.colorWarning)
            }
            Priority.High -> {
                this.priorityBorder.setBackgroundResource(R.color.colorDanger)
            }
            else -> {
                this.priorityBorder.setBackgroundColor(Color.GRAY)
            }
        }
    }
}