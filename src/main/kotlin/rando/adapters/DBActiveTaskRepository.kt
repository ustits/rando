package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.ActiveTaskRepository
import rando.domain.Todo

class DBActiveTaskRepository : ActiveTaskRepository {

    override fun findByTodo(todo: Todo): ActiveTask? {
        return transaction {
            val statement = prepareStatement("""
                SELECT tasks.id, tasks.text FROM tasks, todos_active_task 
                WHERE todos_active_task.todo = ? AND tasks.id = todos_active_task.task
            """.trimIndent())
            statement.setLong(1, todo.id)

            val rs = statement.executeQuery()
            val task = rs.toSequence {
                val id  = rs.getLong(1)
                val text  = rs.getString(2)
                ActiveTask(id = id, text = text)
            }.firstOrNull()

            statement.close()
            task
        }
    }
}
