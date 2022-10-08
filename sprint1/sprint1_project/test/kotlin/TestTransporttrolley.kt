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
            RunTrolley().main()
        }

        waitingForActor("transporttrolley_t")

        obs.addContext("ctxwasteservice_trolley", Pair("localhost", 8050))
        obs.addActor("transporttrolley_t", "ctxwasteservice_trolley")

        obs.createCoapConnection("transporttrolley_t")

        conn = ConnTcp("localhost", 8050)

        ColorsOut.outappl("Setup done.", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        conn.close()
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
            "depositRequest($material, $quantity)", "transporttrolley_t").toString()

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