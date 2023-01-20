import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import org.junit.AfterClass
import org.junit.BeforeClass
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
        fun stopAll() {
            t.stop()
        }
    }

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false

        obs = CoapObserver()
        obs.addContext("ctxwasteservice_test", Pair("localhost", 8050))
        obs.addActor("transporttrolley", "ctxwasteservice_test")
        obs.createCoapConnection("transporttrolley")
        obs.clearCoapHistory()

        CommUtils.delay(2000)

        conn = ConnTcp("localhost", 8050)

        ColorsOut.outappl("Setup done.", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        conn.close()
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
        ColorsOut.out("Closed all connections...", ColorsOut.BLUE)
    }

    @Test
    fun testPlasticDeposit() {
        var answer = simulateRequest(conn, Material.PLASTIC, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_PLASTICBOX)",
            "transporttrolley(arrived_PLASTICBOX)",
            "transporttrolley(depositDone_PLASTIC)",
            "transporttrolley(arrived_HOME)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testGlassDeposit() {
        var answer = simulateRequest(conn, Material.GLASS, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_GLASSBOX)",
            "transporttrolley(arrived_GLASSBOX)",
            "transporttrolley(depositDone_GLASS)",
            "transporttrolley(arrived_HOME)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testPlasticGlassDeposit() {
        var answer = simulateRequest(conn, Material.PLASTIC, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(depositDone_PLASTIC)")

        answer = simulateRequest(conn, Material.GLASS, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_PLASTICBOX)",
            "transporttrolley(arrived_PLASTICBOX)",
            "transporttrolley(depositDone_PLASTIC)",
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_GLASSBOX)",
            "transporttrolley(arrived_GLASSBOX)",
            "transporttrolley(depositDone_GLASS)",
            "transporttrolley(arrived_HOME)",
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testGlassPlasticDeposit() {
        var answer = simulateRequest(conn, Material.GLASS, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(depositDone_GLASS)")

        answer = simulateRequest(conn, Material.PLASTIC, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        if(answer != null)
            assertTrue(answer.contains("pickupDone(OK)"))

        obs.waitForSpecificHistoryEntry("transporttrolley(arrived_HOME)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_GLASSBOX)",
            "transporttrolley(arrived_GLASSBOX)",
            "transporttrolley(depositDone_GLASS)",
            "transporttrolley(handleDepositRequest)",
            "transporttrolley(moving_INDOOR)",
            "transporttrolley(arrived_INDOOR)",
            "transporttrolley(pickupDone)",
            "transporttrolley(moving_PLASTICBOX)",
            "transporttrolley(arrived_PLASTICBOX)",
            "transporttrolley(depositDone_PLASTIC)",
            "transporttrolley(arrived_HOME)",
        )))

        obs.clearCoapHistory()
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