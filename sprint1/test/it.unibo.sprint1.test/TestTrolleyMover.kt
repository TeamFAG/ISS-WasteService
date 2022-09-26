package it.unibo.sprint1.test

import it.unibo.kactor.QakContext.Companion.getActor
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TestTrolleyMover {

    private var obs: ObserverForTest? = null

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false
        SystemConfig.setTheConfiguration("SystemConfiguration")

        obs = ObserverForTest()

        thread {
            RunTestMover().main()
        }

        waitingForActor("trolleymover")
        waitingForActor("pathexecutor")

        obs!!.addContext("ctxbasicrobot", Pair("localhost", 8020))
        obs!!.addContext("ctxwasteservice", Pair("127.0.0.1", 8030))
        obs!!.addActor("trolleymover", "ctxwasteservice")
        obs!!.addActor("pathexecutor", "ctxwasteservice")
        obs!!.addActor("basicrobot", "ctxbasicrobot")

        obs!!.createCoapConnection("trolleymover")

        ColorsOut.out("Setup for test done...", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        obs!!.closeAllCoapConnections()
        obs = null
        ColorsOut.out("Closed all connections...", ColorsOut.BLUE)
    }

    @Test
    fun test() {

    }

    private fun waitingForActor(actorName: String) {
        ColorsOut.out("Waiting for $actorName", ColorsOut.BLUE)
        var actor = getActor(actorName)

        while(actor == null) {
            CommUtils.delay(100)
            actor = getActor(actorName)
        }

        ColorsOut.out("$actorName loaded.", ColorsOut.BLUE)
    }
}