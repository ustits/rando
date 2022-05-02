package rando.adapters

import rando.domain.ID
import rando.domain.Todo
import rando.domain.TodoRepository

class DBTodoRepository : TodoRepository {

    override fun forID(id: ID): Todo = DBTodo(id) { tasks -> tasks.randomOrNull() }

}
