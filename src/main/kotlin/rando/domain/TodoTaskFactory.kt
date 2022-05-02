package rando.domain

interface TodoTaskFactory {

    fun create(todo: Todo, newTask: NewTask): TodoTask

}
