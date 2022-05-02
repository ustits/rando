package rando.domain

interface TodoService {

    fun createTodo(): HashID

    fun findTodoByHashString(str: String): Todo?

    class Impl(
        private val todoFactory: TodoFactory,
        private val todoRepository: TodoRepository,
        private val hashIDs: HashIDs
    ) : TodoService {

        override fun createTodo(): HashID {
            val id = todoFactory.create()
            return hashIDs.fromID(id)
        }

        override fun findTodoByHashString(str: String): Todo? {
            val id = hashIDs.fromString(str)?.toID()
            return if (id != null) {
                todoRepository.forID(id)
            } else {
                null
            }
        }
    }

}
