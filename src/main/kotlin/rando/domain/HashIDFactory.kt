package rando.domain

interface HashIDFactory {

    fun fromStringOrNull(str: String): HashID?

    fun fromID(id: ID): HashID

}
