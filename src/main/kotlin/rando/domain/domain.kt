package rando.domain

typealias Task = String

typealias ID = Long

fun interface HashIDSource : (String) -> HashID?

interface HashID {

    fun toID(): ID

}

fun interface RandomTask : (HashID) -> Task? {

    class Impl(private val todos: Todos) : RandomTask {
        override fun invoke(p1: HashID): Task? {
            return todos.forID(p1.toID()).taskList().randomOrNull()
        }
    }

}
