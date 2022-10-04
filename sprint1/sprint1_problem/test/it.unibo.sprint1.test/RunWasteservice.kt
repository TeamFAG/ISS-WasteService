package it.unibo.sprint1.test

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunWasteservice {
    fun main() = runBlocking {
        QakContext.createContexts(
            "localhost", this, "wasteservice_system.pl", "sysRules.pl", "ctxwasteservice_prob"
        )
    }
}