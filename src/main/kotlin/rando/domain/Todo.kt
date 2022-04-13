package rando.domain

interface Todo {

    fun tasks(): Tasks

    fun add(task: Task)

    fun activeTask(): Task?

    fun changeActiveTask(strategy: (Tasks) -> Task?)

    class InMemory : Todo {

        private val mutableList: MutableList<Task> = mutableListOf()
        private var activeTask: Task? = null

        override fun tasks(): Tasks {
            return Tasks.Stub(mutableList)
        }

        override fun activeTask(): Task? = activeTask

        override fun changeActiveTask(strategy: (Tasks) -> Task?) {
            activeTask = strategy.invoke(tasks())
        }

        override fun add(task: Task) {
            mutableList.add(task)
        }
    }
}
