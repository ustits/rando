package rando.adapters

import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.ActiveTaskRepository
import rando.domain.ID
import rando.domain.NewTask
import rando.domain.Todo
import rando.domain.TodoTask

class DBTodo(
    private val todoID: ID,
    private val activeTaskRepository: ActiveTaskRepository,
    private val nextTaskStrategy: (List<TodoTask>) -> TodoTask?
) : Todo {

    override val id: ID
        get() = todoID

    override fun task(): ActiveTask? {
        val task = getActiveTask()
        return if (task == null) {
            setActiveTask()
            getActiveTask()
        } else {
            task
        }
    }

    private fun getActiveTask(): ActiveTask? {
        return activeTaskRepository.findByTodo(this)
    }

    private fun setActiveTask() {
        val task = nextTaskStrategy.invoke(tasks())
        if (task != null) {
            activeTaskRepository.add(ActiveTask(task, todoID))
        }
    }

    override fun completeTask() {
        getActiveTask()?.let{ complete(it) }
        setActiveTask()
    }

    private fun complete(task: ActiveTask) {
        activeTaskRepository.remove(task)
    }

    override fun add(task: NewTask) {
        transaction {
            val statement = prepareStatement("INSERT INTO tasks (text, todo) VALUES (?, ?)")
            statement.setString(1, task.text)
            statement.setLong(2, todoID)
            statement.execute()
            statement.close()
        }
    }

    private fun tasks(): List<TodoTask> {
        return DBTasks(todoID).todoTasks()
    }
}
