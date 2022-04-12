package rando.adapters

import rando.db.transaction
import rando.domain.ID
import rando.domain.Task
import rando.domain.Tasks
import rando.domain.Todo

class DBTodo(private val id: ID) : Todo {

    override fun tasks(): Tasks = DBTasks(id)

    override fun add(task: Task) {
        transaction {
            val statement = prepareStatement("INSERT INTO tasks (text, todo) VALUES (?, ?)")
            statement.setString(1, task)
            statement.setLong(2, id)
            statement.execute()
            statement.close()
        }
    }
}
