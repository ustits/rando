package rando.domain

interface TodoTaskRepository {

    fun findByTodo(todo: Todo): List<TodoTask>

}
