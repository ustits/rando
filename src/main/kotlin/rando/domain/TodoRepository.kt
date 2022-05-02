package rando.domain

interface TodoRepository {

    fun forID(id: ID): Todo

}
