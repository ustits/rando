package rando

import org.hashids.Hashids
import rando.adapters.HashidsHashIDs
import rando.config.Configuration
import rando.domain.HashIDs
import rando.domain.RandomTask
import rando.domain.Todos

class AppDeps(private val config: Configuration) {

    private val todos = Todos.InMemory()

    fun randomTask(): RandomTask = RandomTask.Impl(todos())

    fun hashIDs(): HashIDs {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return HashidsHashIDs(hashids)
    }

    fun todos(): Todos = todos

}
