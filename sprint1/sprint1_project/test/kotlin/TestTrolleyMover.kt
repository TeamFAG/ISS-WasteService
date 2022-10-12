import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext.Companion.getActor
import org.junit.*
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import kotlin.concurrent.thread
import kotlin.test.assertTrue

class TestTrolleyMover {

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

    @Before
    fun up() {
        CommSystemConfig.tracing = false
        SystemConfig.setTheConfiguration("SystemConfiguration")

        obs = CoapObserver()
        obs.addContext("ctxwasteservice_test", Pair("localhost", 8050))
        obs.addActor("trolleymover", "ctxwasteservice_test")
        obs.createCoapConnection("trolleymover")
        obs.clearCoapHistory()

        CommUtils.delay(2000)

        conn = ConnTcp("localhost", 8050)

        ColorsOut.out("Setup for test done...", ColorsOut.BLUE)
    }

    @After
    fun down() {
        obs.clearCoapHistory()
        obs.closeAllCoapConnections()
        conn.close()
        ColorsOut.out("Closed all connections...", ColorsOut.BLUE)
        ColorsOut.outappl("Test done", ColorsOut.BLUE)
    }

    @Test
    fun testHomeToIndoor() {
        var answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_indoor)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testIndoorToPlasticbox() {
        var answer = simulateRequest(conn, "plasticbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_plasticbox)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_plasticbox)",
            "trolleymover(handlePathDone_plasticbox)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testPlasticboxToHome() {
        var answer = simulateRequest(conn, "home")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_home)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handlePathDone_home)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testIndoorToGlassbox() {
        goToLocation("indoor")

        var answer = simulateRequest(conn, "glassbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_glassbox)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_glassbox)",
            "trolleymover(handlePathDone_glassbox)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testGlassboxToHome() {
        var answer = simulateRequest(conn, "home")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_home)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handlePathDone_home)"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun testGlassToHomeInterruptedToIndoor() {
        goToLocation("glassbox")

        simulateRequestWithoutResponse(conn, "home")
        CommUtils.delay(1000)
        val answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_indoor)")

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handleMovement_stopPath)",
            "trolleymover(handleInterruptedMovement)",
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor)",
        )))

        obs.clearCoapHistory()
    }

    private fun goToLocation(location: String) {
        var answer = simulateRequest(conn, location)
        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)
        obs.waitForSpecificHistoryEntry("trolleymover(handlePathDone_$location)")
        obs.clearCoapHistory()
    }

    private fun simulateRequest(conn: ConnTcp, location: String): String? {
        val request = MsgUtil.buildRequest("test", "move",
            "move($location)", "trolleymover").toString()

        try {
            return conn.request(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun simulateRequestWithoutResponse(conn: ConnTcp, location: String) {
        val request = MsgUtil.buildRequest("test", "move",
            "move($location)", "trolleymover").toString()

        try {
            return conn.requestWithoutResponse(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun waitingForActor(actorName: String) {
        ColorsOut.out("Waiting for $actorName", ColorsOut.BLUE)
        var actor = getActor(actorName)

        while(actor == null) {
            CommUtils.delay(500)
            actor = getActor(actorName)
            println(actor)
        }

        ColorsOut.out("$actorName loaded.", ColorsOut.BLUE)
    }
}