package rando.domain

import kotlin.math.absoluteValue
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

    class InMemory: Todos {

        private val todos: MutableMap<ID, Todo> = mutableMapOf()

        override fun forID(id: ID): Todo {
            return todos[id] ?: error("No element for id: $id")
        }

        override fun create(): ID {
            val id = Random.nextInt().absoluteValue.toLong()
            todos[id] = Todo.InMemory()
            return id
        }
    }

}
