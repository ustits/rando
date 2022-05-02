package rando.domain

interface ActiveTaskRepository {

    fun findByTodo(todo: Todo): ActiveTask?

    fun add(activeTask: ActiveTask)

    fun remove(activeTask: ActiveTask)

}
