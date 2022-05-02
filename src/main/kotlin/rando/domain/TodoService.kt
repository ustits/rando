package rando.domain

interface TodoService {

    fun createTodo(): HashID

    class Impl(
        private val todoFactory: TodoFactory,
        private val hashIDs: HashIDs
    ) : TodoService {

        override fun createTodo(): HashID {
            val id = todoFactory.create()
            return hashIDs.fromID(id)
        }
    }

}
