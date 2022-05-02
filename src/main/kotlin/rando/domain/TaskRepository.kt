package rando.domain

interface TaskRepository {

    fun findTodoTasksByTodo(todo: Todo): List<TodoTask>

    fun findActiveTasksByTodo(todo: Todo): List<ActiveTask>

}
