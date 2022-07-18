package rando.domain

class Todo(
    val id: ID,
    private val taskRepository: TaskRepository,
    private val todoTaskFactory: TodoTaskFactory,
    private val taskPickStrategy: TaskPickStrategy
) {

    fun add(task: NewTask) {
        todoTaskFactory.create(this, task)
    }

    fun activeTask(): ActiveTask? {
        return getActiveTask() ?: assignNextTask()
    }

    fun completeTask() {
        val task = getActiveTask()
        if (task != null) {
            task.complete()
        } else {
            error("Can't completeTask. There is no active task for current todo")
        }
    }

    fun todoTasks(): List<TodoTask> {
        return taskRepository.findTodoTasksByTodo(this)
    }

    private fun getActiveTask(): ActiveTask? {
        return taskRepository.findActiveTasksByTodo(this).firstOrNull()
    }

    private fun assignNextTask(): ActiveTask? {
        return getNextTask()?.activate()
    }

    private fun getNextTask(): TodoTask? {
        val tasks = taskRepository.findTodoTasksByTodo(this)
        return taskPickStrategy.invoke(tasks)
    }

}
