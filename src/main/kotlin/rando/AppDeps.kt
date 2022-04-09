package rando

import org.hashids.Hashids
import rando.adapters.hashidsHashIDSource
import rando.config.Configuration
import rando.domain.HashIDSource
import rando.domain.RandomTask
import rando.domain.Todos

class AppDeps(private val config: Configuration) {

    fun randomTask(): RandomTask = RandomTask.Impl(todos())

    fun hashIDSource(): HashIDSource {
        val hashids = with(config.app.salt) {
            Hashids(text, length)
        }
        return hashidsHashIDSource(hashids)
    }

    private fun todos(): Todos = Todos.Stub()

}
