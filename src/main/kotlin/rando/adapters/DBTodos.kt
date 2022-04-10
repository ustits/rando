package rando.adapters

import rando.db.transaction
import rando.domain.ID
import rando.domain.Todo
import rando.domain.Todos

class DBTodos : Todos {

    override fun forID(id: ID): Todo = DBTodo(id)

    override fun create(): ID =
        transaction {
            val statement = prepareStatement("INSERT INTO todos DEFAULT VALUES RETURNING id")
            val rs = statement.executeQuery()
            val id = rs.getLong(1)
            rs.close()
            statement.close()
            id
        }

}
