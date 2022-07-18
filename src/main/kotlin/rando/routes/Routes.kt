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
import kotlinx.html.details
import kotlinx.html.em
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.li
import kotlinx.html.summary
import kotlinx.html.ul
import rando.domain.NewTask
import rando.domain.TodoService
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

fun Route.createTodo(todoService: TodoService) {
    post<TodosAPI> {
        val hash = todoService.createTodo()
        val todoURL = call.locations.href(TodosAPI.ByHashID(hash))
        call.respondRedirect(todoURL)
    }
}

fun Route.createTask(todoService: TodoService) {
    post<TodosAPI.ByHashID.Task> { loc ->
        val todo = todoService.findTodoByHashString(loc.root.hashID)
        if (todo == null) {
            throw NotFoundException()
        } else {
            val params = call.receiveParameters()
            val text = params.getOrFail("text")
            todo.add(NewTask(text = text))
            val todoURL = locations.href(loc.root)
            call.respondRedirect(todoURL)
        }
    }
}

fun Route.todo(layout: Layout, todoService: TodoService) {
    get<TodosAPI.ByHashID> { loc ->
        val todo = todoService.findTodoByHashString(loc.hashID)
        val taskURL = call.locations.href(TodosAPI.ByHashID.Task(loc))
        val completeTaskURL = call.locations.href(TodosAPI.ByHashID.CompleteTask(loc))

        if (todo == null) {
            throw NotFoundException()
        } else {
            val activeTask = todo.activeTask()
            val todoTasks = todo.todoTasks()
            val completedTasks = todo.completedTasks()
            call.respondHtmlTemplate(layout) {
                content {
                    if (activeTask != null) {
                        h1 {
                            +activeTask.text
                        }
                        form(method = FormMethod.post, action = completeTaskURL) {
                            input(type = InputType.submit) {
                                value = "Complete"
                            }
                        }
                        if (todoTasks.isNotEmpty()) {
                            details {
                                summary { +"To be done" }
                                ul {
                                    todoTasks.forEach {
                                        li { +it.text }
                                    }
                                }
                            }
                        }
                    } else {
                        h1 {
                            +"There are no tasks yet"
                        }
                    }
                    if (completedTasks.isNotEmpty()) {
                        details {
                            summary { +"Done" }
                            completedTasks.groupBy { it.completedAt }.forEach { (time, tasks) ->
                                ul {
                                    li {
                                        em { +time.toString() }
                                        ul {
                                            tasks.forEach { task ->
                                                li {
                                                    +task.text
                                                }
                                            }
                                        }
                                    }
                                }
                            }
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

fun Route.completeTask(todoService: TodoService) {
    post<TodosAPI.ByHashID.CompleteTask> { loc ->
        val todo = todoService.findTodoByHashString(loc.root.hashID)
        val redirect = locations.href(loc.root)

        if (todo == null) {
            throw NotFoundException()
        } else {
            todo.completeTask()
            call.respondRedirect(redirect)
        }
    }
}
