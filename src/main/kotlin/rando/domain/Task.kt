package rando.domain

interface Task {

    fun print(): String

}

class NewTask(private val text: String) : Task {
    override fun print(): String = text
}

interface TodoTask : Task {

    fun id(): ID

    fun complete()

}
