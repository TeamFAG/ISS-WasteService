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

class IntegrationTestGuiUpdater {
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
        obs.addActor("guiupdater", "ctxwasteservice_test")
        obs.addActor("wasteservice", "ctxwasteservice_test")
        obs.createCoapConnection("wasteservice")
        obs.createCoapConnection("guiupdater")

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
    fun integrationTestGuiUpdater() {
        CommUtils.delay(7000)
        val answer = simulateRequest(connTcp, Material.GLASS, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("loadAccepted"))

        obs.waitForSpecificHistoryEntry("guiupdater(HOME Idle)")

        println(obs.getCoapHistory())

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "",
        )))
    }

    private fun simulateRequest(connTcp: ConnTcp, material: Material, quantity: Float): String? {
        val req = MsgUtil.buildRequest("test", "storeRequest", "storeRequest($material, $quantity)", "wasteservice").toString()

        try {
            return connTcp.request(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}