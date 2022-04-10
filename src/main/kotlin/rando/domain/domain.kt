package rando.domain

typealias Task = String

typealias ID = Long

interface HashIDs {

    fun fromString(str: String): HashID?

    fun fromID(id: ID): HashID

}

interface HashID {

    fun toID(): ID

    fun print(): String

}

fun interface RandomTask : (HashID) -> Task? {

    class Impl(private val todos: Todos) : RandomTask {
        override fun invoke(p1: HashID): Task? {
            return todos.forID(p1.toID()).taskList().randomOrNull()
        }
    }

}
