package it.unibo.sprint1.test

import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TestWasteservice {

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false

        var thread = thread {
            RunWasteservice().main()
        }

        var actor = QakContext.getActor("wasteservice")
        while(actor == null) {
            CommUtils.delay(500)
            actor = QakContext.getActor("wasteservice")
            println(actor)
        }
    }

    @AfterTest
    fun down() {
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
        CommUtils.delay(3000)
    }

    @Test
    fun testLoadAcceptedPlastic() {
        val reply = simulateRequest(Material.PLASTIC, 10.0)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadAccepted"))
        }
    }

    @Test
    fun testLoadAcceptedGlass() {
        val reply = simulateRequest(Material.GLASS, 10.0)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadAccepted"))
        }
    }

    @Test
    fun testLoadARejectedPlastic() {
        val reply = simulateRequest(Material.PLASTIC, 150.0)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadRejected"))
        }
    }

    @Test
    fun testLoadARejectedGlass() {
        val reply = simulateRequest(Material.GLASS, 150.0)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadRejected"))
        }
    }

    private fun simulateRequest(material: Material, quantity: Double): String? {
        val req = MsgUtil.buildRequest("test", "storeRequest", "storeRequest($material, $quantity)", "wasteservice").toString()

        try {
            val conn = ConnTcp("localhost", 8010)
            return conn.request(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}