package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.ActiveTaskRepository
import rando.domain.Todo
import java.sql.Connection

class DBActiveTaskRepository : ActiveTaskRepository {

    override fun findByTodo(todo: Todo): ActiveTask? {
        return transaction {
            val statement = prepareStatement(
                """
                SELECT id, text 
                FROM tasks
                WHERE todo = ? AND is_active = true AND completed_at IS NULL
            """.trimIndent()
            )
            statement.setLong(1, todo.id)

            val rs = statement.executeQuery()
            val task = rs.toSequence {
                val id = rs.getLong(1)
                val text = rs.getString(2)
                ActiveTask(id = id, text = text, todoID = todo.id)
            }.firstOrNull()

            statement.close()
            task
        }
    }

    override fun add(activeTask: ActiveTask) {
        transaction {
            changeState(activeTask, true)
        }

    }

    override fun remove(activeTask: ActiveTask) {
        transaction {
            changeState(activeTask, false)
            val st = prepareStatement("UPDATE tasks SET completed_at = date('now') WHERE id = ?")
            st.setLong(1, activeTask.id)
            st.execute()
            st.close()
        }

    }

    private fun Connection.changeState(task: ActiveTask, isActive: Boolean) {
        val statement = prepareStatement("UPDATE tasks SET is_active = ? WHERE id = ?")
        statement.setBoolean(1, isActive)
        statement.setLong(2, task.id)
        statement.execute()
        statement.close()
    }
}
