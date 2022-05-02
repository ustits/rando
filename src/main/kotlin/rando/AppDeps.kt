package rando

import org.hashids.Hashids
import rando.adapters.DBTodoFactory
import rando.adapters.DBTodoRepository
import rando.adapters.HashidsHashIDFactory
import rando.config.Configuration
import rando.domain.HashIDFactory
import rando.domain.TodoService

class AppDeps(private val config: Configuration) {

    fun todoService(): TodoService {
        return TodoService.Impl(
            todoFactory = DBTodoFactory(),
            todoRepository = DBTodoRepository(),
            hashIDFactory = hashIDs()
        )
    }

    private fun hashIDs(): HashIDFactory {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDFactory(hashids)
    }

}
