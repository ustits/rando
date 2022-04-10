package rando.config

import com.zaxxer.hikari.HikariConfig

data class Database(val jdbcURL: String = "jdbc:sqlite::memory:") {

    fun toHikariConfig(): HikariConfig {
        val config = HikariConfig()
        config.jdbcUrl = jdbcURL
        config.isAutoCommit = false
        return config
    }

}
