package it.unibo.sprint1.test

import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext.Companion.getActor
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

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
        obs!!.addContext("ctxwasteservice", Pair("127.0.0.1", 8038))
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
    fun test_home_to_indoor() {
        val conn = ConnTcp("127.0.0.1", 8038)
        val answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        conn.close()

        println(obs?.getCoapHistory())
    }

    private fun simulateRequest(conn: ConnTcp, location: String): String? {
        val request = MsgUtil.buildRequest("transporttrolley", "move",
            "move($location)", "trolleymover").toString()

        try {
            return conn.request(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun waitingForActor(actorName: String) {
        ColorsOut.out("Waiting for $actorName", ColorsOut.BLUE)
        var actor = getActor(actorName)

        while(actor == null) {
            CommUtils.delay(500)
            actor = getActor(actorName)
            println(actor)
        }

        ColorsOut.out("$actorName loaded.", ColorsOut.BLUE)
    }
}