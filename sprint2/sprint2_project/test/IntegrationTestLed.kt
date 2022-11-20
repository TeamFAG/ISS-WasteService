import it.unibo.kactor.MsgUtil
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

class IntegrationTestLed {
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

        CommUtils.delay(2000)

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
    fun integrationTestWithLed() {
        val answer = simulateRequest(connTcp, Material.GLASS, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")

        println(obs.getCoapHistory())

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "led(OFF)",
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "led(BLINKING)",
            "transporttrolley(arrived_INDOOR)",
            "led(ON)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_GLASSBOX)",
            "led(BLINKING)",
            "transporttrolley(arrived_GLASSBOX)",
            "led(ON)",
            "transporttrolley(depositDone_GLASS)",
            "led(BLINKING)",
            "transporttrolley(arrived_HOME)",
            "led(OFF)",
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
}