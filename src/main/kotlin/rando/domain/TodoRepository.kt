package rando.domain

interface TodoRepository {

    fun findByIDOrNull(id: ID): Todo?

}
