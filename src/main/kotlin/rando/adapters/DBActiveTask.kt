package rando.adapters

import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.ID

class DBActiveTask(private val id: ID, private val todoID: ID, private val text: String) : ActiveTask {

    override fun print(): String = text

    override fun id(): ID = id

    override fun complete() {
        transaction {
            val delete = prepareStatement("DELETE FROM todos_active_task WHERE todo = ?")
            delete.setLong(1, todoID)
            delete.execute()
            delete.close()

            val update = prepareStatement("UPDATE tasks SET completed = true WHERE id = ?")
            update.setLong(1, id)
            update.execute()
            update.close()
        }
    }
}