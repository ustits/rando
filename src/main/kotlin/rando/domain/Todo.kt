package rando.domain

interface Todo {

    fun add(task: NewTask)

    fun task(): TodoTask?

    fun completeTask()

}
