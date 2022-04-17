package rando.domain

interface Tasks {

    fun asList(): List<Task>

    fun todoTasks(): List<TodoTask>

    class Stub(private val todoTasks: List<TodoTask>) : Tasks {
        override fun asList(): List<Task> = todoTasks
        override fun todoTasks(): List<TodoTask> = todoTasks
    }

}
