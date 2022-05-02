package rando.domain

sealed class Task

@JvmInline
value class NewTask(val text: String)

data class TodoTask(val id: ID, val text: String) : Task()

data class ActiveTask(val id: ID, val text: String, val todoID: ID) : Task() {

    constructor(todoTask: TodoTask, todoID: ID) : this(todoTask.id, todoTask.text, todoID)

}
