package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.Todo
import rando.domain.TodoTaskRepository
import rando.domain.TodoTask

class DBTodoTaskRepository : TodoTaskRepository {

    override fun findByTodo(todo: Todo): List<TodoTask> =
        transaction {
            val statement = prepareStatement("""
                SELECT id, text 
                FROM tasks
                WHERE todo = ? AND is_active = false AND completed_at IS NULL
            """.trimIndent())
            statement.setLong(1, todo.id)
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