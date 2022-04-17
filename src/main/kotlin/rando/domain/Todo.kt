package rando.domain

interface Todo {

    fun tasks(): Tasks

    fun add(task: NewTask)

    fun activeTask(): TodoTask?

    fun changeActiveTask(strategy: (Tasks) -> TodoTask?)

    class InMemory : Todo {

        private val mutableList: MutableList<TodoTask> = mutableListOf()
        private var activeTask: TodoTask? = null

        override fun tasks(): Tasks {
            return Tasks.Stub(mutableList)
        }

        override fun activeTask(): TodoTask? = activeTask

        override fun changeActiveTask(strategy: (Tasks) -> TodoTask?) {
            activeTask = strategy.invoke(tasks())
        }

        override fun add(task: NewTask) {
            mutableList.add(TodoTask.Stub(task))
        }
    }
}
