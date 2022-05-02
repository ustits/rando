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
            val tasks = todoTaskRepository.findByTodo(this)
            val task = taskPickStrategy.invoke(tasks)
            if (task != null) {
                activeTaskRepository.add(ActiveTask(task, id))
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

    }

}
