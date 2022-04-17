package rando.domain

typealias ID = Long

fun interface TaskSource : (HashID) -> Task? {

    class Random(private val todos: Todos) : TaskSource {
        override fun invoke(p1: HashID): Task? {
            return todos.forHashID(p1).tasks().asList().randomOrNull()
        }
    }

    class Active(private val todos: Todos) : TaskSource {
        override fun invoke(p1: HashID): Task? {
            val todo = todos.forHashID(p1)
            val activeTask = todo.activeTask()
            return if (activeTask == null) {
                todo.changeActiveTask { tasks -> tasks.todoTasks().randomOrNull() }
                todo.activeTask()
            } else {
                activeTask
            }
        }
    }

}
