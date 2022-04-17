package rando.domain

interface Todos {

    fun forHashID(hashID: HashID): Todo {
        return forID(hashID.toID())
    }

    fun forID(id: ID): Todo

    fun create(): ID

}
