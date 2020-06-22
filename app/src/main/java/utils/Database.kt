package utils

import models.Task
import java.util.*
import kotlin.collections.ArrayList

object Database {
    var tasks: ArrayList<Task> = ArrayList<Task>()

    fun getAllTasks(): List<Task> {
        return this.tasks.toList()
    }

    fun addTask(task: Task) {
        task.id = UUID.randomUUID()
        this.tasks.add(task)
    }

    fun removeTask(taskId: UUID) {
        val dbTask: Task? = this.tasks.find { it.id == taskId }

        if (dbTask != null) {
            this.tasks.remove(dbTask)
        }
    }

    fun toggleTask(taskId: UUID?, isCompleted: Boolean) {
        val dbTask: Task? = this.tasks.find { it.id == taskId }
        dbTask?.isComplete = isCompleted
    }
}