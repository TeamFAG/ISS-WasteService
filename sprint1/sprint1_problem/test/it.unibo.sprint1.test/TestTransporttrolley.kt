package it.unibo.sprint1.test

import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.concurrent.thread
import kotlin.test.assertTrue

class TestTransporttrolley {

    private lateinit var obs: CoapObserver
    private lateinit var conn: ConnTcp

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false

        obs = CoapObserver()

        var thread = thread {
            RunWasteservice().main()
        }

        waitingForActor("transporttrolley")

        obs.addContext("ctxwasteservice", Pair("localhost", 8010))
        obs.addActor("transporttrolley", "ctxwasteservice")

        obs.createCoapConnection("transporttrolley")

        conn = ConnTcp("localhost", 8010)

        ColorsOut.outappl("Setup done.", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        conn.close()
        CommUtils.delay(3000)
    }

    @Test
    fun testPlasticDepostit() {
        var answer = simulateRequest(conn, Material.PLASTIC, 10F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(ok)"))

        CommUtils.delay(5000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_Indoor)",
            "transporttrolley(arrived_Indoor)",
            "transporttrolley(moving_PlasticBox)",
            "transporttrolley(arrived_PlasticBox)",
            "transporttrolley(depositDone)",
            "transporttrolley(moving_Home)",
            "transporttrolley(arrived_Home)"
        )))
    }

    @Test
    fun testGlassDeposit() {
        var answer = simulateRequest(conn, Material.GLASS, 10F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(ok)"))

        CommUtils.delay(5000)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_Indoor)",
            "transporttrolley(arrived_Indoor)",
            "transporttrolley(moving_GlassBox)",
            "transporttrolley(arrived_GlassBox)",
            "transporttrolley(depositDone)",
            "transporttrolley(moving_Home)",
            "transporttrolley(arrived_Home)"
        )))
    }

    private fun simulateRequest(conn: ConnTcp, material: Material, quantity: Float): String? {
        val request = MsgUtil.buildRequest("test", "depositRequest",
            "depositRequest($material, $quantity)", "transporttrolley").toString()

        try {
            return conn.request(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun waitingForActor(actorName: String) {
        ColorsOut.out("Waiting for $actorName", ColorsOut.BLUE)
        var actor = QakContext.getActor(actorName)

        while(actor == null) {
            CommUtils.delay(500)
            actor = QakContext.getActor(actorName)
            println(actor)
        }

        ColorsOut.out("$actorName loaded.", ColorsOut.BLUE)
    }
}