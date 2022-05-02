package rando.domain

interface ActiveTaskRepository {

    fun findByTodo(todo: Todo): ActiveTask?

}
