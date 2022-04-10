package rando.routes

import io.ktor.locations.*
import rando.domain.HashID

@Location("/todos")
object TodosAPI {

    @Location("/{hashID}")
    class ByHashID(val todos: TodosAPI = TodosAPI, val hashID: String) {

        constructor(hashID: HashID) : this(hashID = hashID.print())

        @Location("/tasks")
        class Task(val root: ByHashID)

    }

}
