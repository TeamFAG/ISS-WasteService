import it.unibo.kactor.MsgUtil
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.LedState
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

class UnitTestLed {
    private lateinit var obs: CoapObserver
    private lateinit var connTcp: ConnTcp

    companion object {
        lateinit var t: Thread

        @BeforeClass
        @JvmStatic
        fun prepareTest() {
            t = Thread {
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
    fun testLedOn() {
        simulateForward(connTcp, LedState.ON)

        obs.waitForSpecificHistoryEntry("led(ON)")

        assertTrue(obs.checkIfHystoryContains("led(ON)"))
    }

    @Test
    fun testLedOff() {
        simulateForward(connTcp, LedState.OFF)

        obs.waitForSpecificHistoryEntry("led(OFF)")

        assertTrue(obs.checkIfHystoryContains("led(OFF)"))
    }

    @Test
    fun testLedBlink() {
        simulateForward(connTcp, LedState.BLINKING)

        obs.waitForSpecificHistoryEntry("led(BLINKING)")

        simulateForward(connTcp, LedState.OFF)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "led(BLINKING)",
            "led(OFF)"
        )))
    }

    private fun simulateForward(connTcp: ConnTcp, ledState: LedState) {
        val dispatch = MsgUtil.buildDispatch("test", "updateLed", "updateLed($ledState)", "led").toString()

        try {
            connTcp.forward(dispatch)
        } catch (e: Exception) {

        }
    }
}