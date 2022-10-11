import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import ws.WasteServiceStatusManager
import kotlin.concurrent.thread
import kotlin.test.*

class TestWasteservice {

    private lateinit var obs: CoapObserver

    companion object {
        var currentP = 0F
        var currentG = 0F

        @BeforeClass
        @JvmStatic
        fun prepareTest() {
            thread {
                it.unibo.ctxwasteservice_test.main()
            }
        }

        @AfterClass
        @JvmStatic
        fun stopAll() {

        }
    }

    @BeforeTest
    fun up() {
        CommSystemConfig.tracing = false

        obs = CoapObserver()

        obs.addContext("ctxwasteservice_test", Pair("localhost", 8050))
        obs.addActor("wasteservice", "ctxwasteservice_test")

        obs.createCoapConnection("wasteservice")

        CommUtils.delay(2000)

        ColorsOut.outappl("Setup for test done...", ColorsOut.BLUE)
    }

    @AfterTest
    fun down() {
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        ColorsOut.outappl("Closed all connections...", ColorsOut.BLUE)
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
    }

    @Test
    fun testLoadAcceptedPlastic() {
        val material = Material.PLASTIC
        val qty = 10F
        currentP += qty

        val reply = simulateRequest(material, qty)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadAccepted"))
        }

        obs.waitForSpecificHistoryEntry("wasteservice(handlePickupReply_loadAccepted)")

        obs.getCoapHistory().iterator().forEach{e -> ColorsOut.outappl(e, ColorsOut.YELLOW)}
        println("Plastic: ${currentP}")
        println("Glass: ${currentG}")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "wasteservice(handleStoreRequest_${material}_${qty})",
            "wasteservice(Plastic: ${currentP})",
            "wasteservice(Glass: ${currentG})",
            "wasteservice(handlePickupReply_loadAccepted)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testLoadAcceptedGlass() {
        val material = Material.GLASS
        val qty = 10F
        currentG += qty

        val reply = simulateRequest(material, qty)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadAccepted"))
        }

        obs.waitForSpecificHistoryEntry("wasteservice(handlePickupReply_loadAccepted)")

        obs.getCoapHistory().iterator().forEach{e -> ColorsOut.outappl(e, ColorsOut.YELLOW)}
        println("Plastic: ${currentP}")
        println("Glass: ${currentG}")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "wasteservice(handleStoreRequest_${material}_${qty})",
            "wasteservice(Plastic: ${currentP})",
            "wasteservice(Glass: ${currentG})",
            "wasteservice(handlePickupReply_loadAccepted)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testLoadRejectedPlastic() {
        val material = Material.PLASTIC
        val qty = 150F

        val reply = simulateRequest(material, qty)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadRejected"))
        }

        CommUtils.delay(1000)

        obs.getCoapHistory().iterator().forEach{e -> println(e)}

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "wasteservice(handleStoreRequest_${material}_${qty})",
            "wasteservice(handleStoreRequest_loadRejected)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testLoadRejectedGlass() {
        val material = Material.GLASS
        val qty = 150F

        val reply = simulateRequest(material, qty)
        ColorsOut.outappl("Reply: $reply", ColorsOut.GREEN)
        if (reply != null) {
            assertTrue(reply.contains("loadRejected"))
        }

        CommUtils.delay(1000)

        obs.getCoapHistory().iterator().forEach{e -> println(e)}

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "wasteservice(handleStoreRequest_${material}_${qty})",
            "wasteservice(handleStoreRequest_loadRejected)"
        )))

        obs.clearCoapHistory()
    }

    private fun simulateRequest(material: Material, quantity: Float): String? {
        val req = MsgUtil.buildRequest("test", "storeRequest", "storeRequest($material, $quantity)", "wasteservice").toString()

        try {
            val conn = ConnTcp("localhost", 8050)
            return conn.request(req)
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
            println("Actor: $actor")
        }

        ColorsOut.out("$actorName loaded.", ColorsOut.BLUE)
    }
}