package it.unibo.sprint1.test

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunWasteservice {
    fun main() = runBlocking {
        QakContext.createContexts(
            "127.0.0.1", this, "wasteservice_problem_analysis.pl", "sysRules.pl", "ctxwasteservice"
        )
    }
}