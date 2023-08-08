package com.matteblack.fsm

import com.matteblack.fsm.annotations.TaskItem
import java.util.ArrayList

abstract class TaskState : BotState() {

    private var tasks: List<Task> = ArrayList()

    open fun defineTasks() {
        tasks = ArrayList()
        this::class.java.declaredFields
            .filter { it.isAnnotationPresent(TaskItem::class.java) }
            .sortedBy { it.getAnnotation(TaskItem::class.java).order }
            .forEach { field ->
                field.isAccessible = true
                if (Task::class.java.isAssignableFrom(field.type)) {
                    tasks += field.get(this) as Task
                } else {
                    throw IllegalArgumentException("Field ${field.name} is annotated with @TaskItem but is not of type Task")
                }

            }
    }

    fun addTask(task: Task): Task {
        tasks += task

        return task
    }

    override fun execute() {
        for (task in tasks) {
            if (task.validate()) {
                task.execute()
                return
            }
        }
    }

}