package it.unibo.sprint1.test

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunTransporttrolley {
    fun main() = runBlocking {
        QakContext.createContexts(
            "127.0.0.1", this, "transporttrolley_system.pl", "sysRules.pl", "ctxwasteservice_trolley"
        )
    }
}