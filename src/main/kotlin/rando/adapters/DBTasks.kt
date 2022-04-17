package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ID
import rando.domain.Task
import rando.domain.Tasks
import rando.domain.TodoTask

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
                val text = getString(1)
                Task.Stub(text)
            }.toList()
            statement.close()
            tasks
        }

    override fun todoTasks(): List<TodoTask> =
        transaction {
            val statement = prepareStatement("""
                SELECT id, text FROM tasks
                WHERE tasks.todo = ? AND completed = false
            """.trimIndent())
            statement.setLong(1, todoID)
            val rs = statement.executeQuery()

            val tasks = rs.toSequence {
                val id = getLong(1)
                val text = getString(2)
                DBTodoTask(id, text)
            }.toList()
            statement.close()
            tasks
        }

}