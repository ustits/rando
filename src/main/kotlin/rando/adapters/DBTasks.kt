package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ID
import rando.domain.Task
import rando.domain.Tasks

class DBTasks(private val todoID: ID) : Tasks {

    override fun asList(): List<Task> =
        transaction {
            val statement = prepareStatement("""
                SELECT text FROM tasks 
                WHERE tasks.todo = ?
            """.trimIndent())
            statement.setLong(1, todoID)
            val rs = statement.executeQuery()
            val tasks = rs.toSequence {
                getString(1)
            }.toList()
            statement.close()
            tasks
        }
}