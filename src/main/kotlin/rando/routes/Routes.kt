package rando.routes

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.input
import kotlinx.html.label
import rando.domain.HashIDs
import rando.domain.RandomTask
import rando.domain.Todos
import rando.html.Layout

fun Route.index(layout: Layout) {
    val newTodoURL = application.locations.href(TodosAPI)
    get("/") {
        call.respondHtmlTemplate(layout) {
            content {
                form(method = FormMethod.post, action = newTodoURL) {
                    input(type = InputType.submit) {
                        value = "Create todo"
                    }
                }
            }
        }
    }
}

fun Route.createTodo(hashIDs: HashIDs, todos: Todos) {
    post<TodosAPI> {
        val id = todos.create()
        val hash = hashIDs.fromID(id)
        val todoURL = call.locations.href(TodosAPI.ByHashID(hash))
        call.respondRedirect(todoURL)
    }
}

fun Route.createTask(hashIDs: HashIDs, todos: Todos) {
    post<TodosAPI.ByHashID.Task> { loc ->
        val hashID = hashIDs.fromString(loc.root.hashID)
        val todoURL = locations.href(loc.root)

        if (hashID == null) {
            throw NotFoundException()
        } else {
            val params = call.receiveParameters()
            val text = params.getOrFail("text")
            val id = hashID.toID()
            todos.forID(id).add(text)
            call.respondRedirect(todoURL)
        }
    }
}

fun Route.randomTask(layout: Layout, hashIDs: HashIDs, randomTask: RandomTask) {
    get<TodosAPI.ByHashID> { loc ->
        val hashID = hashIDs.fromString(loc.hashID)
        val taskURL = call.locations.href(TodosAPI.ByHashID.Task(loc))

        if (hashID == null) {
            throw NotFoundException()
        } else {
            val task = randomTask(hashID)
            call.respondHtmlTemplate(layout) {
                content {
                    if (task != null) {
                        h1 {
                            +task
                        }
                    } else {
                        h1 {
                            +"There are no tasks yet"
                        }
                    }

                    form(method = FormMethod.post, action = taskURL) {
                        label {
                            +"Task"
                        }
                        input(type = InputType.text, name = "text") {
                            required = true
                        }
                        input(type = InputType.submit) {
                            value = "Create task"
                        }
                    }
                }
            }
        }
    }
}
