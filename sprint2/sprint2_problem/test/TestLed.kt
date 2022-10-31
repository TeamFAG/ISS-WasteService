import it.unibo.kactor.MsgUtil
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import kotlin.concurrent.thread
import kotlin.test.*

class TestLed {

    private lateinit var obs: CoapObserver
    private lateinit var connTcp: ConnTcp

    companion object {
        lateinit var t: Thread

        @BeforeClass
        @JvmStatic
        fun prepareTest() {
            t = thread {
                it.unibo.ctxwasteservice_test.main()
            }
        }

        @AfterClass
        @JvmStatic
        fun endTest() {
            t.stop()
        }
    }

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false
        SystemConfig.setTheConfiguration("SystemConfiguration")

        obs = CoapObserver()
        obs.addContext("ctxwasteservice_test", Pair("localhost", 8050))
        obs.addActor("led", "ctxwasteservice_test")
        obs.addActor("transporttrolley", "ctxwasteservice_test")
        obs.createCoapConnection("transporttrolley")
        obs.createCoapConnection("led")

        CommUtils.delay(2000)

        connTcp = ConnTcp("localhost", 8050)

        ColorsOut.outappl("Setup done.", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        connTcp.close()
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
        ColorsOut.outappl("Closed all connections", ColorsOut.BLUE)
    }

    @Test
    fun testLedNormalRoutine() {
        var answer = simulateRequest(connTcp, Material.GLASS, 5F)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")
        obs.waitForSpecificHistoryEntry("led(OFF)")
        obs.filterDefinitelyHistory("led")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "led(OFF)",
            "led(BLINKING)",
            "led(ON)",
            "led(BLINKING)",
            "led(ON)",
            "led(BLINKING)",
            "led(OFF)"
        )))
    }

    @Test
    fun testLedHaltedRoutine() {
        var answer = simulateRequest(connTcp, Material.GLASS, 5F)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(pickupDone)")

        CommUtils.delay(1000)

        simulateHalt(connTcp, 5000)

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")
        obs.waitForSpecificHistoryEntry("led(OFF)")
        obs.filterDefinitelyHistory("led")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "led(OFF)",
            "led(BLINKING)",
            "led(ON)",
            "led(BLINKING)",
            "led(ON)",
            "led(BLINKING)",
            "led(ON)",
            "led(BLINKING)",
            "led(OFF)"
        )))
    }

    private fun simulateHalt(conn: ConnTcp, time: Int) {
        var evt = MsgUtil.buildEvent("test", "distance", "distance(${SystemConfig.DLIMIT - 1})").toString()
        conn.forward(evt)
        CommUtils.delay(time)
        evt = MsgUtil.buildEvent("test", "distance", "distance(${SystemConfig.DLIMIT + 1})").toString()
        conn.forward(evt)
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
}