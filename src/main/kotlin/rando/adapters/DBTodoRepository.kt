package rando.adapters

import rando.domain.ID
import rando.domain.TaskPickStrategy
import rando.domain.Todo
import rando.domain.TodoRepository

class DBTodoRepository : TodoRepository {

    override fun forID(id: ID): Todo = DBTodo(
        todoID = id,
        activeTaskRepository = DBActiveTaskRepository(),
        todoTaskFactory = DBTodoTaskFactory(),
        todoTaskRepository = DBTodoTaskRepository(),
        taskPickStrategy = TaskPickStrategy.Random()
    )

}
