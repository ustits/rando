package rando.domain

interface Todo {

    fun taskList(): List<Task>

    fun add(task: Task)

    class InMemory : Todo {

        private val mutableList: MutableList<Task> = mutableListOf()

        override fun taskList(): List<Task> {
            return mutableList
        }

        override fun add(task: Task) {
            mutableList.add(task)
        }
    }
}
