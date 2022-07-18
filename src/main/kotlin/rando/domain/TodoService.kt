package rando.domain

class TodoService(
    private val todoFactory: TodoFactory,
    private val todoRepository: TodoRepository,
    private val hashIDFactory: HashIDFactory
) {

    fun createTodo(): HashID {
        val id = todoFactory.create()
        return hashIDFactory.fromID(id)
    }

    fun findTodoByHashString(str: String): Todo? {
        val id = hashIDFactory.fromStringOrNull(str)?.toID()
        return if (id != null) {
            todoRepository.findByIDOrNull(id)
        } else {
            null
        }
    }

}
