package rando

import org.hashids.Hashids
import rando.adapters.DBTodoFactory
import rando.adapters.DBTodoRepository
import rando.adapters.HashidsHashIDs
import rando.config.Configuration
import rando.domain.HashIDs
import rando.domain.TodoService
import rando.domain.TodoRepository

class AppDeps(private val config: Configuration) {

    fun hashIDs(): HashIDs {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDs(hashids)
    }

    fun todos(): TodoRepository = DBTodoRepository()

    fun todoService(): TodoService {
        return TodoService.Impl(
            todoFactory = DBTodoFactory(),
            hashIDs = hashIDs()
        )
    }

}
