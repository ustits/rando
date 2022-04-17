package rando.adapters

import rando.db.transaction
import rando.domain.ID
import rando.domain.NewTask
import rando.domain.Task
import rando.domain.Tasks
import rando.domain.Todo
import rando.domain.TodoTask

class DBTodo(private val id: ID) : Todo {

    override fun tasks(): Tasks = DBTasks(id)

    override fun activeTask(): TodoTask? {
        return transaction {
            val statement = prepareStatement("""
                SELECT tasks.id, tasks.text FROM tasks, todos_active_task 
                WHERE todos_active_task.todo = ? AND tasks.id = todos_active_task.task
            """.trimIndent())
            statement.setLong(1, id)

            val rs = statement.executeQuery()
            val task = if (rs.next()) {
                val id  = rs.getLong(1)
                val text  = rs.getString(2)
                DBTodoTask(id, text)
            } else {
                null
            }
            rs.close()
            statement.close()
            task
        }
    }

    override fun changeActiveTask(strategy: (Tasks) -> TodoTask?) {
        val task = strategy.invoke(tasks())
        if (task != null) {
            changeActiveTask(task)
        }
    }

    private fun changeActiveTask(task: TodoTask) {
        transaction {
            val statement = prepareStatement("INSERT INTO todos_active_task (todo, task) VALUES (?, ?)")
            statement.setLong(1, id)
            statement.setLong(2, task.id())
            statement.execute()
            statement.close()
        }
    }

    override fun add(task: NewTask) {
        transaction {
            val statement = prepareStatement("INSERT INTO tasks (text, todo) VALUES (?, ?)")
            statement.setString(1, task.print())
            statement.setLong(2, id)
            statement.execute()
            statement.close()
        }
    }
}
