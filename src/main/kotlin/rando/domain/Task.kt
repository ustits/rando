package rando.domain

import kotlin.random.Random

interface Task {

    fun print(): String

    class Stub(private val text: String) : Task {
        override fun print(): String = text
    }
}

class NewTask(private val text: String) : Task {
    override fun print(): String = text
}

interface TodoTask : Task {

    fun id(): ID

    fun complete()

    class Stub(private val id: ID, private val text: String) : TodoTask {

        constructor(task: Task) : this(id = Random.nextLong(), text = task.print())

        override fun print(): String = text

        override fun id(): ID = id

        override fun complete() {
        }
    }

}
