package rando.adapters

import rando.db.toSequence
import rando.db.transaction
import rando.domain.NewTask
import rando.domain.Todo
import rando.domain.TodoTask
import rando.domain.TodoTaskFactory

class DBTodoTaskFactory : TodoTaskFactory {

    override fun create(todo: Todo, newTask: NewTask): TodoTask {
        return transaction {
            val statement = prepareStatement("INSERT INTO tasks (text, todo) VALUES (?, ?) RETURNING id, text")
            statement.setString(1, newTask.text)
            statement.setLong(2, todo.id)
            val task = statement.executeQuery().toSequence {
                DBTodoTask(
                    getLong(1),
                    getString(2)
                )
            }.first()
            statement.close()
            task
        }
    }
}
