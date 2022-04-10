package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ID
import rando.domain.Task
import rando.domain.Todo

class DBTodo(private val id: ID) : Todo {

    override fun taskList(): List<Task> =
        transaction {
            val statement = prepareStatement("""
                SELECT tasks.value FROM todos, tasks 
                WHERE todos.id = ? AND todos.id = tasks.todo
            """.trimIndent())
            statement.setLong(1, id)
            val rs = statement.executeQuery()
            val tasks = rs.toSequence {
                getString(1)
            }.toList()
            statement.close()
            tasks
        }

    override fun add(task: Task) {
        transaction {
            val statement = prepareStatement("INSERT INTO tasks (value, todo) VALUES (?, ?)")
            statement.setString(1, task)
            statement.setLong(2, id)
            statement.execute()
            statement.close()
        }
    }
}
