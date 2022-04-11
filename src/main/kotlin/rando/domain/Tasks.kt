package rando.domain

interface Tasks {

    fun asList(): List<Task>

    class Stub(private val list: List<Task>) : Tasks {
        override fun asList(): List<Task> = list
    }

}
