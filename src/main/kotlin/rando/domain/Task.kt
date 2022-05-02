package rando.domain

sealed class Task

@JvmInline
value class NewTask(val text: String)

data class TodoTask(val id: ID, val text: String) : Task()
