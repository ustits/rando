package rando.adapters

import rando.db.transaction
import rando.domain.ActiveTask
import rando.domain.CompletedTask
import rando.domain.ID
import java.sql.Connection

class DBActiveTask(override val id: ID, override val text: String) : ActiveTask {

    override fun complete(): CompletedTask {
        transaction {
            deactivate()
            complete()
        }
        return DBCompletedTask(id, text)
    }

    private fun Connection.deactivate() {
        val statement = prepareStatement("UPDATE tasks SET is_active = false WHERE id = ?")
        statement.setLong(1, id)
        statement.execute()
        statement.close()
    }

    private fun Connection.complete() {
        val st = prepareStatement("UPDATE tasks SET completed_at = date('now') WHERE id = ?")
        st.setLong(1, id)
        st.execute()
        st.close()
    }
}
