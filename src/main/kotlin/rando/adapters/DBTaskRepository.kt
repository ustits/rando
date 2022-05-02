package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.Todo
import rando.domain.TaskRepository
import rando.domain.TodoTask
import java.sql.Connection
import java.sql.PreparedStatement

class DBTaskRepository : TaskRepository {

    override fun findTodoTasksByTodo(todo: Todo): List<TodoTask> {
        return transaction {
            val statement = statementFor(todo, false)
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


    override fun findActiveTasksByTodo(todo: Todo): List<ActiveTask> {
        return transaction {
            val statement = statementFor(todo, true)
            val rs = statement.executeQuery()
            val tasks = rs.toSequence {
                val id = rs.getLong(1)
                val text = rs.getString(2)
                DBActiveTask(id = id, text = text)
            }.toList()
            statement.close()
            tasks
        }
    }

    private fun Connection.statementFor(todo: Todo, isActive: Boolean): PreparedStatement {
        val statement = prepareStatement(
            """
                SELECT id, text 
                FROM tasks
                WHERE todo = ? AND is_active = ? AND completed_at IS NULL
            """.trimIndent()
        )
        statement.setLong(1, todo.id)
        statement.setBoolean(2, isActive)
        return statement
    }

}
