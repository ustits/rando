package rando.domain

interface Todo {

    fun tasks(): Tasks

    fun add(task: Task)

    class InMemory : Todo {

        private val mutableList: MutableList<Task> = mutableListOf()

        override fun tasks(): Tasks {
            return Tasks.Stub(mutableList)
        }

        override fun add(task: Task) {
            mutableList.add(task)
        }
    }
}
