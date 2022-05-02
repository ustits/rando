package rando.adapters

import org.hashids.Hashids
import rando.domain.HashID
import rando.domain.HashIDFactory
import rando.domain.ID

class HashidsHashIDFactory(private val hashids: Hashids) : HashIDFactory {
    override fun fromStringOrNull(str: String): HashID? {
        return hashids.longOrNull(str)?.let {
            object : HashID {
                override fun toID(): ID = it
                override fun print(): String = str
            }
        }
    }

    override fun fromID(id: ID): HashID {
        val str = hashids.encode(id)
        return object :HashID {
            override fun toID(): ID = id
            override fun print(): String = str
        }
    }
}

private fun Hashids.longOrNull(str: String): Long? {
    val array = decode(str)
    return if (array.isEmpty()) {
        null
    } else {
        array[0]
    }
}