package rando

import rando.plugins.configureCallLogging
import rando.plugins.configureMetrics
import rando.plugins.configureRouting
import rando.config.readConfiguration
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import rando.db.configureDatabase

fun main() {
    val cfg = readConfiguration()

    embeddedServer(Netty, port = cfg.server.port, host = "0.0.0.0") {
        val appDeps = AppDeps(cfg)
        configureDatabase(cfg.database)
        configureRouting(appDeps)
        configureCallLogging()
        configureMetrics()
    }.start(wait = true)
}
