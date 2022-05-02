package rando.domain

interface Task {

    fun print(): String

}

@JvmInline
value class NewTask(val text: String)

interface TodoTask : Task {

    fun id(): ID

    fun complete()

}
