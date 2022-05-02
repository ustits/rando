package rando.adapters

import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.ID
import rando.domain.TodoTask

class DBTodoTask(override val id: ID, override val text: String) : TodoTask {

    override fun activate(): ActiveTask {
        transaction {
            val statement = prepareStatement("UPDATE tasks SET is_active = true WHERE id = ?")
            statement.setLong(1, id)
            statement.execute()
            statement.close()
        }
        return DBActiveTask(id, text)
    }
}
