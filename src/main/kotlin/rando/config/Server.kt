package rando.config

data class Server(
    val port: Int = 8080,
    val baseURL: String = "http://localhost:8080"
)
