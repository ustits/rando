package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ID
import rando.domain.TaskPickStrategy
import rando.domain.Todo
import rando.domain.TodoRepository

class DBTodoRepository : TodoRepository {

    override fun findByIDOrNull(id: ID): Todo? {
        return if (exists(id)) {
            Todo(
                id = id,
                todoTaskFactory = DBTodoTaskFactory(),
                taskRepository = DBTaskRepository(),
                taskPickStrategy = TaskPickStrategy.Random()
            )
        } else {
            null
        }
    }

    private fun exists(id: ID): Boolean {
        return transaction {
            val statement = prepareStatement("SELECT COUNT(id) FROM todos WHERE id = ?")
            statement.setLong(1, id)
            val count = statement.executeQuery().toSequence {
                getLong(1)
            }.first()
            statement.close()
            count > 0
        }
    }

}
