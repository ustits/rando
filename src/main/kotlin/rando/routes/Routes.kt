package rando.routes

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.button
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.input
import rando.domain.HashIDs
import rando.domain.RandomTask
import rando.domain.Todos
import rando.html.Layout

fun Route.index(layout: Layout) {
    get("/") {
        call.respondHtmlTemplate(layout) {
            content {
                h1 {
                    form(method = FormMethod.post, action = "/create") {
                        input(type = InputType.submit) {
                            value = "Create todo"
                        }
                    }
                }
            }
        }
    }
}

fun Route.createTodo(hashIDs: HashIDs, todos: Todos) {
    post("/create") {
        val id = todos.create()
        val hash = hashIDs.fromID(id)
        call.respondRedirect("/todo/${hash.print()}")
    }
}

fun Route.randomTask(layout: Layout, hashIDs: HashIDs, randomTask: RandomTask) {
    get("/todo/{hashID}") {
        val hashString = call.parameters["hashID"].orEmpty()
        val hashID = hashIDs.fromString(hashString)

        if (hashID == null) {
            throw NotFoundException()
        } else {
            val task = randomTask(hashID)
            if (task == null) {
                call.respondHtmlTemplate(layout) {
                    content {
                        button {
                            +"Create task"
                        }
                    }
                }
            } else {
                call.respondHtmlTemplate(layout) {
                    content {
                        h1 {
                            +task
                        }
                    }
                }
            }
        }
    }
}
