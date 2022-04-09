package rando.adapters

import org.hashids.Hashids
import rando.domain.HashID
import rando.domain.HashIDSource
import rando.domain.ID

fun hashidsHashIDSource(hashids: Hashids): HashIDSource = HashIDSource { str ->
    hashids.longOrNull(str)?.let {
        object : HashID {
            override fun toID(): ID = it
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