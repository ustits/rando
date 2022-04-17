package rando.domain

interface Task {

    fun id(): ID

    fun print(): String

    class New(private val text: String) : Task {
        override fun id(): ID = error("New task can't have id")
        override fun print(): String = text
    }

    class Stub(private val id: ID, private val text: String) : Task {
        override fun id(): ID = id
        override fun print(): String = text
    }

}
