package it.unibo.sprint1.test

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunTestMover {
    fun main() = runBlocking {
        QakContext.createContexts(
            "127.0.0.1", this, "trolley_problem.pl", "sysRules.pl", "ctxwasteservice"
        )
    }
}