package rando.db

import java.sql.ResultSet
import java.time.LocalDate

fun ResultSet.getLocalDate(field: String): LocalDate {
    return LocalDate.parse(getString(field))
}
