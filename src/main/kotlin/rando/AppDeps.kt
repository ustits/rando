package rando

import org.hashids.Hashids
import rando.adapters.DBTodoFactory
import rando.adapters.DBTodoRepository
import rando.adapters.HashidsHashIDs
import rando.config.Configuration
import rando.domain.HashIDs
import rando.domain.TodoService

class AppDeps(private val config: Configuration) {

    fun todoService(): TodoService {
        return TodoService.Impl(
            todoFactory = DBTodoFactory(),
            todoRepository = DBTodoRepository(),
            hashIDs = hashIDs()
        )
    }

    private fun hashIDs(): HashIDs {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDs(hashids)
    }

}
