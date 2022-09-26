package it.unibo.sprint1.test

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunTestMover {
    fun main() = runBlocking {
        QakContext.createContexts(
            "localhost", this, "trolley_problem.pl", "sysRules.pl"
        )
    }
}