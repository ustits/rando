package rando

import org.hashids.Hashids
import rando.adapters.DBTodos
import rando.adapters.HashidsHashIDs
import rando.config.Configuration
import rando.domain.HashIDs
import rando.domain.TaskSource
import rando.domain.Todos

class AppDeps(private val config: Configuration) {

    fun taskSource(): TaskSource = TaskSource.Active(todos())

    fun hashIDs(): HashIDs {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDs(hashids)
    }

    fun todos(): Todos = DBTodos()

}
