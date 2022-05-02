package rando.adapters

import rando.domain.ActiveTask
import rando.domain.ActiveTaskRepository
import rando.domain.ID
import rando.domain.NewTask
import rando.domain.Todo
import rando.domain.TodoTask
import rando.domain.TodoTaskFactory
import rando.domain.TodoTaskRepository

class DBTodo(
    private val todoID: ID,
    private val activeTaskRepository: ActiveTaskRepository,
    private val todoTaskRepository: TodoTaskRepository,
    private val todoTaskFactory: TodoTaskFactory,
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
        todoTaskFactory.create(this, task)
    }

    private fun tasks(): List<TodoTask> {
        return todoTaskRepository.findByTodo(this)
    }
}
