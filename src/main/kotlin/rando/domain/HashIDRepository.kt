package rando.domain

interface HashIDRepository {

    fun fromString(str: String): HashID?

    fun fromID(id: ID): HashID

}
