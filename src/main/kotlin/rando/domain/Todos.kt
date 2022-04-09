package rando.domain

import kotlin.random.Random

interface Todos {

    fun forID(id: ID): Todo

    fun create(): ID

    class Stub(
        private val todo: Todo = Todo.InMemory(),
        private val id: ID = Random.nextLong()
    ) : Todos {
        override fun forID(id: ID): Todo = todo
        override fun create(): ID = id
    }

}
