package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.ID
import rando.domain.NewTask
import rando.domain.Todo
import rando.domain.TodoTask

class DBTodo(private val todoID: ID, private val nextTaskStrategy: (List<TodoTask>) -> TodoTask?) : Todo {

    override fun task(): TodoTask? {
        val task = getActiveTask()
        return if (task == null) {
            setActiveTask()
            getActiveTask()
        } else {
            task
        }
    }

    private fun getActiveTask(): TodoTask? {
        return transaction {
            val statement = prepareStatement("""
                SELECT tasks.id, tasks.text FROM tasks, todos_active_task 
                WHERE todos_active_task.todo = ? AND tasks.id = todos_active_task.task
            """.trimIndent())
            statement.setLong(1, todoID)

            val rs = statement.executeQuery()
            val task = rs.toSequence {
                val id  = rs.getLong(1)
                val text  = rs.getString(2)
                DBTodoTask(id = id, text = text)
            }.firstOrNull()

            statement.close()
            task
        }
    }

    private fun setActiveTask() {
        val task = nextTaskStrategy.invoke(tasks())
        if (task != null) {
            transaction {
                val statement = prepareStatement("INSERT INTO todos_active_task (task, todo) VALUES(?, ?)")
                statement.setLong(1, task.id())
                statement.setLong(2, todoID)
                statement.execute()
                statement.close()
            }
        }
    }

    override fun completeTask() {
        getActiveTask()?.complete()
        setActiveTask()
    }

    override fun add(task: NewTask) {
        transaction {
            val statement = prepareStatement("INSERT INTO tasks (text, todo) VALUES (?, ?)")
            statement.setString(1, task.print())
            statement.setLong(2, todoID)
            statement.execute()
            statement.close()
        }
    }

    private fun tasks(): List<TodoTask> {
        return DBTasks(todoID).todoTasks()
    }
}
