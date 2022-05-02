package rando.domain

interface Todo {

    val id: ID

    fun add(task: NewTask)

    fun task(): ActiveTask?

    fun completeTask()

    class Impl(
        override val id: ID,
        private val activeTaskRepository: ActiveTaskRepository,
        private val todoTaskRepository: TodoTaskRepository,
        private val todoTaskFactory: TodoTaskFactory,
        private val taskPickStrategy: TaskPickStrategy
    ) : Todo {

        override fun task(): ActiveTask? {
            return getActiveTask() ?: assignNextTask()
        }

        override fun completeTask() {
            val task = getActiveTask()
            if (task != null) {
                task.complete()
            } else {
                error("Can't completeTask. There is no active task for current todo")
            }
        }

        override fun add(task: NewTask) {
            todoTaskFactory.create(this, task)
        }

        private fun getActiveTask(): ActiveTask? {
            return activeTaskRepository.findByTodo(this)
        }

        private fun assignNextTask(): ActiveTask? {
            return getNextTask()?.activate()
        }

        private fun getNextTask(): TodoTask? {
            val tasks = todoTaskRepository.findByTodo(this)
            return taskPickStrategy.invoke(tasks)
        }

    }

}
