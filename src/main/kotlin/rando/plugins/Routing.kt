package rando.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.webjars.*
import kotlinx.html.p
import rando.AppDeps
import rando.html.Layout
import rando.routes.createTask
import rando.routes.createTodo
import rando.routes.index
import rando.routes.listTasks
import rando.routes.todo

fun Application.configureRouting(appDeps: AppDeps) {
    install(Locations) {
    }

    val layout = Layout()

    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respondHtmlTemplate(layout) {
                content {
                    p {
                        +"There is nothing here"
                    }
                }
            }
        }
    }

    routing {
        index(layout)
        todo(
            layout = layout,
            hashIDs = appDeps.hashIDs(),
            randomTask = appDeps.randomTask()
        )
        createTodo(
            hashIDs = appDeps.hashIDs(),
            todos = appDeps.todos()
        )
        createTask(
            hashIDs = appDeps.hashIDs(),
            todos = appDeps.todos()
        )
        listTasks(
            layout = layout,
            hashIDs = appDeps.hashIDs(),
            todos = appDeps.todos()
        )

        static("assets") {
            resources("js")
            resources("css")
            resources("img")
        }
        install(Webjars) {
            path = "assets"
        }
    }
}
