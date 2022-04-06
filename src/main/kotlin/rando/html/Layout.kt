package rando.html

import io.ktor.html.*
import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.footer
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.small
import kotlinx.html.title

class Layout : Template<HTML> {

    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            title { +"Rando" }
            meta { charset = "UTF-8" }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1"
            }

            link(rel = "stylesheet", href = "/assets/pico/css/pico.min.css")
            script { src = "/assets/htmx.org/htmx.min.js" }
            script { src = "/assets/hyperscript.org/_hyperscript_web.min.js" }
        }
        body {
            main(classes = "container") {
                insert(content)
            }
            footer("container") {
                small {
                    p { +"by Ruslan Ustits" }
                    a(href = "https://github.com/ustits/rando") { +"Github" }
                }
            }
        }
    }
}
