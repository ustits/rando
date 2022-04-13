package rando.domain

interface HashIDs {

    fun fromString(str: String): HashID?

    fun fromID(id: ID): HashID

}
