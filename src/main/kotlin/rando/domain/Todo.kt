package rando.domain

interface Todo {

    val id: ID

    fun add(task: NewTask)

    fun task(): ActiveTask?

    fun completeTask()

}
