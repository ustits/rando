package rando.domain

interface TaskPickStrategy : (List<TodoTask>) -> TodoTask? {

    class Random : TaskPickStrategy {
        override fun invoke(p1: List<TodoTask>): TodoTask? {
            return p1.randomOrNull()
        }
    }

}
