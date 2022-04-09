package rando.routes

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.button
import kotlinx.html.h1
import rando.domain.HashIDSource
import rando.domain.RandomTask
import rando.html.Layout

fun Route.index(layout: Layout) {
    get("/") {
        call.respondHtmlTemplate(layout) {
            content {
                h1 {
                    button {
                        +"Create todo"
                    }
                }
            }
        }
    }
}

fun Route.randomTask(layout: Layout, hashIDSource: HashIDSource, randomTask: RandomTask) {
    get("/{hashID}") {
        val hashString = call.parameters["hashID"].orEmpty()
        val hashID = hashIDSource(hashString)

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
