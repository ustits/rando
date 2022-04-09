package rando

import rando.plugins.configureCallLogging
import rando.plugins.configureMetrics
import rando.plugins.configureRouting
import rando.config.readConfiguration
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val cfg = readConfiguration()

    embeddedServer(Netty, port = cfg.server.port, host = "0.0.0.0") {
        val appDeps = AppDeps(cfg)
        configureRouting(appDeps)
        configureCallLogging()
        configureMetrics()
    }.start(wait = true)
}
