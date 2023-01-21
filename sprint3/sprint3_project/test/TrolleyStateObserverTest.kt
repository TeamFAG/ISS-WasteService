import it.unibo.kactor.MsgUtil
import org.junit.AfterClass
import org.junit.BeforeClass
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import ws.Material
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TrolleyStateObserverTest {
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
        obs.addActor("trolleystateobserver", "ctxwasteservice_test")
        obs.addActor("transporttrolley", "ctxwasteservice_test")
        obs.addActor("pather", "ctxwasteservice_test")
        obs.createCoapConnection("trolleystateobserver")
        obs.createCoapConnection("transporttrolley")
        obs.createCoapConnection("pather")

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
    fun testPlasticRoutineUpdates() {
        val answer = simulateRequest(connTcp, Material.PLASTIC, 5F)

        ColorsOut.outappl("Answer: $answer", ColorsOut.GREEN)

        obs.waitForSpecificHistoryEntry("trolleystateobserver(PICKUP)")

        println(obs.getCoapHistory())

        // TRATTO HOME to INDOOR
        // STATE (deve essere moving)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(MOVING)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(moving_INDOOR)"))

        var tsoIndex = obs.getHistoryIndex("trolleystateobserver(MOVING)")
        var otherIndex = obs.getHistoryIndex("transporttrolley(moving_INDOOR)")

        assertTrue(tsoIndex > otherIndex)

        // POSITION (deve essere INDOOR)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(arrived_INDOOR)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(arrived_INDOOR)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(arrived_INDOOR)")
        otherIndex = obs.getHistoryIndex("transporttrolley(arrived_INDOOR)")

        assertTrue(tsoIndex > otherIndex)

        // STATE (deve essere PICKUP)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(PICKUP)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(PICKUP)")

        assertTrue(tsoIndex > otherIndex)

        obs.removeHistoryInterval(0, tsoIndex)

        obs.waitForSpecificHistoryEntry("trolleystateobserver(DEPOSIT)")

        println(obs.getCoapHistory())

        // TRATTO INDOOR to PLASTICBOX
        // STATE (deve essere moving)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(MOVING)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(moving_PLASTICBOX)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(MOVING)")
        otherIndex = obs.getHistoryIndex("transporttrolley(moving_PLASTICBOX)")

        assertTrue(tsoIndex > otherIndex)

        // POSITION (deve essere PLASTICBOX)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(arrived_PLASTICBOX)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(arrived_PLASTICBOX)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(arrived_PLASTICBOX)")
        otherIndex = obs.getHistoryIndex("transporttrolley(arrived_PLASTICBOX)")

        assertTrue(tsoIndex > otherIndex)

        // STATE (deve essere DEPOSIT)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(DEPOSIT)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(depositing)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(DEPOSIT)")
        otherIndex = obs.getHistoryIndex("transporttrolley(depositing)")

        assertTrue(tsoIndex > otherIndex)

        obs.removeHistoryInterval(0, tsoIndex)

        obs.waitForSpecificHistoryEntry("trolleystateobserver(arrived_HOME)")

        println(obs.getCoapHistory())

        // TRATTO PLASTICBOX to HOME
        // STATE (deve essere moving)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(MOVING)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(depositDone_PLASTIC)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(MOVING)")
        otherIndex = obs.getHistoryIndex("transporttrolley(depositDone_PLASTIC)")

        assertTrue(tsoIndex > otherIndex)

        // POSITION (deve essere HOME)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(arrived_HOME)"))
        assertTrue(obs.checkIfHystoryContains("transporttrolley(arrived_HOME)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(arrived_HOME)")
        otherIndex = obs.getHistoryIndex("transporttrolley(arrived_HOME)")

        assertTrue(tsoIndex > otherIndex)

        // STATE (deve essere IDLE)
        assertTrue(obs.checkIfHystoryContains("trolleystateobserver(IDLE)"))

        tsoIndex = obs.getHistoryIndex("trolleystateobserver(IDLE)")

        assertTrue(tsoIndex > otherIndex)
    }

    private fun simulateRequest(connTcp: ConnTcp, material: Material, quantity: Float): String? {
        val req = MsgUtil.buildRequest("test", "depositRequest", "depositRequest($material, $quantity)", "transporttrolley").toString()

        try {
            return connTcp.request(req)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}