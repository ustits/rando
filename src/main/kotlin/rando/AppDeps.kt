package rando

import org.hashids.Hashids
import rando.adapters.DBTodoFactory
import rando.adapters.DBTodoRepository
import rando.adapters.HashidsHashIDRepository
import rando.config.Configuration
import rando.domain.HashIDRepository
import rando.domain.TodoService

class AppDeps(private val config: Configuration) {

    fun todoService(): TodoService {
        return TodoService.Impl(
            todoFactory = DBTodoFactory(),
            todoRepository = DBTodoRepository(),
            hashIDRepository = hashIDs()
        )
    }

    private fun hashIDs(): HashIDRepository {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDRepository(hashids)
    }

}
