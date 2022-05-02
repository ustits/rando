package rando.domain

interface TodoService {

    fun createTodo(): HashID

    fun findTodoByHashString(str: String): Todo?

    class Impl(
        private val todoFactory: TodoFactory,
        private val todoRepository: TodoRepository,
        private val hashIDFactory: HashIDFactory
    ) : TodoService {

        override fun createTodo(): HashID {
            val id = todoFactory.create()
            return hashIDFactory.fromID(id)
        }

        override fun findTodoByHashString(str: String): Todo? {
            val id = hashIDFactory.fromStringOrNull(str)?.toID()
            return if (id != null) {
                todoRepository.findByIDOrNull(id)
            } else {
                null
            }
        }
    }

}
