package rando.domain

interface TodoService {

    fun createTodo(): HashID

    fun findTodoByHashString(str: String): Todo?

    class Impl(
        private val todoFactory: TodoFactory,
        private val todoRepository: TodoRepository,
        private val hashIDRepository: HashIDRepository
    ) : TodoService {

        override fun createTodo(): HashID {
            val id = todoFactory.create()
            return hashIDRepository.fromID(id)
        }

        override fun findTodoByHashString(str: String): Todo? {
            val id = hashIDRepository.fromString(str)?.toID()
            return if (id != null) {
                todoRepository.findByIDOrNull(id)
            } else {
                null
            }
        }
    }

}
