package rando.domain

typealias ID = Long

fun interface TaskSource : (HashID) -> Task? {

    class Random(private val todos: Todos) : TaskSource {
        override fun invoke(p1: HashID): Task? {
            return todos.forID(p1.toID()).tasks().asList().randomOrNull()
        }
    }

    class Active(private val todos: Todos) : TaskSource {
        override fun invoke(p1: HashID): Task? {
            val todo = todos.forID(p1.toID())
            val activeTask = todo.activeTask()
            return if (activeTask == null) {
                todo.changeActiveTask { tasks -> tasks.asList().randomOrNull() }
                todo.activeTask()
            } else {
                activeTask
            }
        }
    }

}
