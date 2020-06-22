package com.example.to_doapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import models.Priority
import models.Task
import utils.Database
import utils.DateUtils
import java.time.LocalDate
import java.util.*


class TaskFormActivity : AppCompatActivity() {
    private lateinit var nameField: EditText
    private lateinit var descriptionField: EditText
    private lateinit var startDateField: EditText
    private lateinit var startTimeField: EditText
    private lateinit var endDateField: EditText
    private lateinit var endTimeField: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var notificationsSwitch: Switch

    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        this.nameField = this.findViewById(R.id.name_field)
        this.descriptionField = this.findViewById(R.id.description_field)
        this.startDateField = this.findViewById(R.id.start_date_field)
        this.startTimeField = this.findViewById(R.id.start_time_field)
        this.endDateField = this.findViewById(R.id.end_date_field)
        this.endTimeField = this.findViewById(R.id.end_time_field)
        this.prioritySpinner = this.findViewById(R.id.priority_spinner)
        this.notificationsSwitch = this.findViewById(R.id.notifications_switch)
        this.saveButton = this.findViewById(R.id.save_btn)

        val time = this.intent.getLongExtra("startDate", 0)

        if (time > 0) {
            val date = Date(time)

            this.startDateField.setText(DateUtils.stringifyDate(date))
        }

        val spinnerArray: MutableList<String> = ArrayList()
        spinnerArray.add(0, "Приоритет(няма)")
        spinnerArray.add(Priority.Low.value, "Нисък")
        spinnerArray.add(Priority.Medium.value, "Среден")
        spinnerArray.add(Priority.High.value, "Висок")

        val adapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item, spinnerArray
        )

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        this.prioritySpinner.adapter = adapter

        this.saveButton.setOnClickListener {
            this.saveTask()
        }
    }

    private fun saveTask() {
        val name: String = this.nameField.text.toString()
        val description: String? = this.descriptionField.text.toString()
        val startDate: Date? = DateUtils
            .parse(this.startDateField.text.toString(), this.startTimeField.text.toString())
        val endDate: Date? = DateUtils
            .parse(this.endDateField.text.toString(), this.endTimeField.text.toString())

        val hasNotifications: Boolean = this.notificationsSwitch.isChecked
        val priority: Priority? = Priority.values().firstOrNull() { it.value == this.prioritySpinner.selectedItemPosition }

        val task: Task = Task(name, description, startDate, endDate, priority, hasNotifications)
        Database.addTask(task)

        this.onBackPressed()
    }
}