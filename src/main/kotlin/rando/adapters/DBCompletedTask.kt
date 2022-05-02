package rando.adapters

import rando.domain.CompletedTask
import rando.domain.ID

class DBCompletedTask(override val id: ID, override val text: String) : CompletedTask
