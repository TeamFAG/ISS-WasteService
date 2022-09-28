package it.unibo.sprint1.test

import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext.Companion.getActor
import org.junit.AfterClass
import org.junit.BeforeClass
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TestTrolleyMover {

    private lateinit var obs: ObserverForTest
    private lateinit var conn: ConnTcp

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false
        SystemConfig.setTheConfiguration("SystemConfiguration")

        obs = ObserverForTest()

        thread {
            RunMover().main()
        }

        waitingForActor("trolleymover")
        waitingForActor("pathexecutor")

        obs.addContext("ctxbasicrobot", Pair("localhost", 8020))
        obs.addContext("ctxwasteservice", Pair("127.0.0.1", 8038))
        obs.addActor("trolleymover", "ctxwasteservice")
        obs.addActor("pathexecutor", "ctxwasteservice")
        obs.addActor("basicrobot", "ctxbasicrobot")

        obs.createCoapConnection("trolleymover")

        conn = ConnTcp("127.0.0.1", 8038)

        ColorsOut.out("Setup for test done...", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        obs.closeAllCoapConnections()
        conn.close()
        ColorsOut.out("Closed all connections...", ColorsOut.BLUE)
    }

    @Test
    fun test() {
        //---------------------------------------HOME TO INDOOR--------------------------------------------//
        var answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        CommUtils.delay(1000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor)"
        )))

        obs.clearCoapHistory()

        //---------------------------------------INDOOR TO PLASTICBOX-----------------------------------------//
        answer = simulateRequest(conn, "plasticbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        println(obs.getCoapHistory())

        CommUtils.delay(1000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_plasticbox)",
            "trolleymover(handlePathDone_plasticbox)"
        )))

        obs.clearCoapHistory()

        //---------------------------------------PLASTICBOX TO HOME INTERRUPTED TO INDOOR--------------------//
        simulateRequestWithoutResponse(conn, "home")
        CommUtils.delay(6000)
        answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        CommUtils.delay(1000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handleMovement_stopPath)",
            "trolleymover(handleInterruptedMovement)",
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor)",
        )))

        obs.clearCoapHistory()


        //---------------------------------------INDOOR TO GLASSBOX-----------------------------------------//
        answer = simulateRequest(conn, "glassbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        CommUtils.delay(1000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_glassbox)",
            "trolleymover(handlePathDone_glassbox)"
        )))

        obs.clearCoapHistory()

        //---------------------------------------GLASSBOX TO HOME-----------------------------------------//
        answer = simulateRequest(conn, "home")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        CommUtils.delay(1000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handlePathDone_home)"
        )))

        obs.clearCoapHistory()
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

    private fun simulateRequestWithoutResponse(conn: ConnTcp, location: String) {
        val request = MsgUtil.buildRequest("transporttrolley", "move",
            "move($location)", "trolleymover").toString()

        try {
            return conn.requestWithoutResponse(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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