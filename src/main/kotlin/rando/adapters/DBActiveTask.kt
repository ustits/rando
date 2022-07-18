package rando.adapters

import rando.db.getLocalDate
import rando.db.toSequence
import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.CompletedTask
import rando.domain.ID
import java.sql.Connection
import java.time.LocalDate

class DBActiveTask(override val id: ID, override val text: String) : ActiveTask {

    override fun complete(): CompletedTask {
        val completedAt = transaction {
            deactivate()
            complete()
        }
        return DBCompletedTask(id, text, completedAt)
    }

    private fun Connection.deactivate() {
        val statement = prepareStatement("UPDATE tasks SET is_active = false WHERE id = ?")
        statement.setLong(1, id)
        statement.execute()
        statement.close()
    }

    private fun Connection.complete(): LocalDate {
        val st = prepareStatement("UPDATE tasks SET completed_at = date('now') WHERE id = ? RETURNING completed_at")
        st.setLong(1, id)
        val rs = st.executeQuery()
        val date = rs.toSequence {
            getLocalDate("completed_at")
        }.first()
        st.close()
        return date
    }
}
