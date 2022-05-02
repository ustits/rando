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
                ActiveTask(id = id, text = text, todoID = todo.id)
            }.firstOrNull()

            statement.close()
            task
        }
    }

    override fun add(activeTask: ActiveTask) {
        transaction {
            val statement = prepareStatement("INSERT INTO todos_active_task (task, todo) VALUES(?, ?)")
            statement.setLong(1, activeTask.id)
            statement.setLong(2, activeTask.todoID)
            statement.execute()
            statement.close()
        }
    }

    override fun remove(activeTask: ActiveTask) {
        transaction {
            val statement = prepareStatement("DELETE FROM tasks WHERE id = ?")
            statement.setLong(1, activeTask.id)
            statement.execute()
            statement.close()
        }
    }
}
