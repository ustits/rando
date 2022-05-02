package rando.adapters

import rando.db.transaction
import rando.domain.ID
import rando.domain.TodoFactory

class DBTodoFactory : TodoFactory {

    override fun create(): ID {
        return transaction {
            val statement = prepareStatement("INSERT INTO todos DEFAULT VALUES RETURNING id")
            val rs = statement.executeQuery()
            val id = rs.getLong(1)
            rs.close()
            statement.close()
            id
        }
    }
}