package com.example.to_doapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import models.Priority
import models.Task
import utils.Database
import utils.DateUtils
import java.util.*

class MainActivity : AppCompatActivity() {

    private var date: Date? = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.findViewById<CalendarView>(R.id.calendar).setOnDateChangeListener { view, year, month, dayOfMonth ->
            this.date = DateUtils.createDate(year, month, dayOfMonth)
        }

        this.findViewById<Button>(R.id.new_task_btn).setOnClickListener {
            this.openTaskForm()
        }

        this.findViewById<Button>(R.id.tasks_btn).setOnClickListener {
            this.openTasksList()
        }

        this.findViewById<Button>(R.id.premium_btn).setOnClickListener {
            this.openPayPremium()
        }

        this.hideAd()
        this.seedDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openTaskForm() {
        val intent = Intent(this, TaskFormActivity::class.java).apply {
            putExtra("startDate", date?.time)
        }

        startActivity(intent)
    }

    private fun openTasksList() {
        val intent = Intent(this, TasksListActivity::class.java).apply { }
        startActivity(intent)
    }

    private fun openPayPremium() {
        val intent = Intent(this, PayForPremiumActivity::class.java).apply { }
        startActivity(intent)
    }

    private fun hideAd() {
        val hideAfterMs: Long = 30 * 1000

        val ad: ImageView = this.findViewById(R.id.ad_view)
        ad.postDelayed(Runnable {
                ad.visibility = View.GONE
        }, hideAfterMs) // (3000 == 3secs)
    }

    private fun seedDatabase() {
        if (Database.getAllTasks().isEmpty()) {
            Database.addTask(Task("Направи това", "шалялял", Date(), null, Priority.High, false))
            Database.addTask(Task("Направи онова", "шалялял", null, Date(), Priority.Low, false))
            Database.addTask(Task("Направи го сега", "шалялял", Date(), Date(), Priority.Medium, false))
        }
    }
}