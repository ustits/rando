package rando.domain

interface Todo {

    fun tasks(): Tasks

    fun add(task: NewTask)

    fun activeTask(): ActiveTask?

    fun completeTask()

}
