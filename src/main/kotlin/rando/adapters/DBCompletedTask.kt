package rando.adapters

import rando.domain.CompletedTask
import rando.domain.ID
import java.time.LocalDate

class DBCompletedTask(
    override val id: ID,
    override val text: String,
    override val completedAt: LocalDate
) : CompletedTask
