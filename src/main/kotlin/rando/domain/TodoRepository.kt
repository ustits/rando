package rando.domain

interface TodoRepository {

    fun forHashID(hashID: HashID): Todo {
        return forID(hashID.toID())
    }

    fun forID(id: ID): Todo

}
