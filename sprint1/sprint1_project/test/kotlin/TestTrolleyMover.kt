import it.unibo.kactor.MsgUtil
import it.unibo.kactor.QakContext.Companion.getActor
import org.junit.*
import org.junit.runners.MethodSorters
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommSystemConfig
import unibo.comm22.utils.CommUtils
import kotlin.concurrent.thread
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    fun A_testHomeToIndoor() {
        var answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_indoor")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 0)
        assertEquals(coord.y, 4)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor, POS: ${coord.x}_${coord.y})"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun B_testIndoorToPlasticbox() {
        var answer = simulateRequest(conn, "plasticbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_plasticbox")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 6)
        //assert(coord.y == 2 || coord.y == 3 || coord.y == 4)
        assert(coord.y == 4)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_plasticbox)",
            "trolleymover(handlePathDone_plasticbox, POS: ${coord.x}_${coord.y})"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun C_testPlasticboxToHome() {
        var answer = simulateRequest(conn, "home")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_home")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 0)
        assertEquals(coord.y, 0)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handlePathDone_home, POS: ${coord.x}_${coord.y})"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun D_testIndoorToGlassbox() {
        goToLocation("indoor")

        var answer = simulateRequest(conn, "glassbox")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_glassbox")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 4)
        assertEquals(coord.y, 0)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_glassbox)",
            "trolleymover(handlePathDone_glassbox, POS: ${coord.x}_${coord.y})"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun E_testGlassboxToHome() {
        var answer = simulateRequest(conn, "home")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_home")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 0)
        assertEquals(coord.y, 0)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handlePathDone_home, POS: ${coord.x}_${coord.y})"
        )))

        obs.clearCoapHistory()
    }

    @Test
    fun F_testGlassToHomeInterruptedToIndoor() {
        goToLocation("glassbox")

        simulateRequestWithoutResponse(conn, "home")
        CommUtils.delay(1000)
        val answer = simulateRequest(conn, "indoor")

        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)

        if (answer != null) {
            assertTrue(answer.contains("moveDone(OK)"))
        }

        val s = obs.waitForSpecificHistoryEntry("handlePathDone_indoor")
        val coord = getCoordFromHistory(s)
        ColorsOut.outappl("Coord: (${coord.x}, ${coord.y})", ColorsOut.YELLOW)

        assertEquals(coord.x, 2)
        assertEquals(coord.y, 4)

        assertTrue(obs.checkIfHystoryContainsOrdered(listOf(
            "trolleymover(handleMovement_home)",
            "trolleymover(handleMovement_stopPath)",
            "trolleymover(handleInterruptedMovement)",
            "trolleymover(handleMovement_indoor)",
            "trolleymover(handlePathDone_indoor, POS: ${coord.x}_${coord.y})",
        )))

        obs.clearCoapHistory()
    }

    private fun getCoordFromHistory(str: String): Coordinate {
        // "trolleymover(handlePathDone_$Loc, POS: ${pos.x}_${pos.y})"
        val s = str.split("POS:")[1].replace(" ", "").replace(")", "")
        val c = s.split("_")
        return Coordinate(c[0].toInt(), c[1].toInt())
    }

    private fun goToLocation(location: String) {
        var answer = simulateRequest(conn, location)
        ColorsOut.out("Answer: $answer", ColorsOut.GREEN)
        obs.waitForSpecificHistoryEntry("handlePathDone_$location")
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