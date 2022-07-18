package rando.domain

import java.time.LocalDate

sealed interface Task {

    val id: ID

    val text: String

}

@JvmInline
value class NewTask(val text: String)

interface TodoTask : Task {

    fun activate(): ActiveTask

}

interface ActiveTask: Task {

    fun complete(): CompletedTask

}

interface CompletedTask : Task {

    val completedAt: LocalDate

}
