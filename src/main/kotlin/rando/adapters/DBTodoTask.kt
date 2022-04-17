package rando.adapters

import rando.db.transaction
import rando.domain.ID
import rando.domain.TodoTask

class DBTodoTask(private val id: ID, private val text: String) : TodoTask {

    override fun print(): String = text

    override fun id(): ID = id

    override fun complete() {
        transaction {
            val statement = prepareStatement("UPDATE tasks SET completed = false WHERE id = ?")
            statement.setLong(1, id)
            statement.execute()
            statement.close()
        }
    }
}
